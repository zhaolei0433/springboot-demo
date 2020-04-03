package com.example.utilsdemo.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

	public static String doGet(String url) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			String res = EntityUtils.toString(response.getEntity());
			return res;
		} catch (IOException e) {
			LOGGER.error( "***doGet throw exception:{}  url:{}", e.getMessage(), url);
		}
		return null;
	}

	public static String doPost(String url, Map<String, String> param,String accessToken) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
				httpPost.setEntity(entity);
			}
			httpPost.setHeader("accessToken",accessToken);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}

	/**
	 * post请求（用于请求json格式的参数）
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String defaultDoPost(String url, String params)
			throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;

		try {

			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			LOGGER.info("***state:" + state);
			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			} else {

			}
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 带证书调用微信接口
	 * 
	 * @param certFilePath
	 *            证书文件所在路径
	 * @param mchId
	 *            微信上商户ID
	 * @param httpUrl
	 * @param reqData
	 */
	public static String doPostWithSSL(String certFilePath, String mchId,
			String httpUrl, String reqData) {
		KeyStore keyStore = null;
		FileInputStream instream = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		try {
			KeyStore.getInstance("PKCS12");
			keyStore.load(instream, mchId.toCharArray());
			instream = new FileInputStream(new File(certFilePath));// P12文件路径
			SSLContext sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, mchId.toCharArray()).build();
			LOGGER.info("***sslcontext==null:" + (sslcontext == null));
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext,
					new String[] { "TLSv1" },
					null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			LOGGER.info("***sslsf==null:" + (sslsf == null));
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.build();
			LOGGER.info("***httpclient==null:" + (httpclient == null)
					+ " url:" + httpUrl);

			HttpPost httpost = new HttpPost(httpUrl); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(reqData, "UTF-8"));
			LOGGER.info("****start to execute");
			response = httpclient.execute(httpost);

			LOGGER.info("***finished execute");
			HttpEntity entity = response.getEntity();
			LOGGER.info("***entity==null:" + (entity == null));
			String responseData = EntityUtils.toString(response.getEntity(),
					"UTF-8");
			LOGGER.info("***responseData:" + responseData);
			return responseData;
		} catch (Exception e) {
			LOGGER.info("***doPostWithSSL throw e:{}, reqData:{}",
					e.getMessage(), reqData);
			return null;
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e) {
					LOGGER.error("***instream.close() throw e:{}",
							e.getMessage());
				}
				instream = null;
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error( "***response.close() throw e:{}",
							e.getMessage());
				}
			}
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error("***httpclient.close() throw e:{}",
							e.getMessage());
				}
			}

		}

	}

	// 上传图片素材到微信服务器
	public static String uploadImageToWx(String accessToken, String filePath) {
		try {
			// 上传文件请求路径
			String action = "http://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="
					+ accessToken + "&type=image";

			URL url = new URL(action);
			String result = null;
			File file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				throw new IOException("上传的文件不存在");
			}
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存

			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			// 设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);

			// 请求正文信息
			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
					+ file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());

			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();

			// 结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;

			try {
				// 定义BufferedReader输入流来读取URL的响应
				reader = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				if (result == null) {
					result = buffer.toString();
				}
			} catch (IOException e) {
				System.out.println("发送POST请求出现异常！" + e);
				e.printStackTrace();
				throw new IOException("数据读取异常");
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
			LOGGER.info("****wx image url:" + result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}