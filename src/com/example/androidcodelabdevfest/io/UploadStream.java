package com.example.androidcodelabdevfest.io;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.androidcodelabdevfest.utils.Constants;

public class UploadStream {

	private static final String FILE_PARAMETER = "upload";// file
	private static final String DESCRIPTION_PARAMETER = "title";// file-name

	public static void uploadFile(final String filePath,
			final String description) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost postRequest = new HttpPost(Constants.BASE_URL
							+ Constants.UPLOAD_PATH);

					MultipartEntity reqEntity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);
					reqEntity.addPart(DESCRIPTION_PARAMETER, new StringBody(
							description));

					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					FileInputStream input = new FileInputStream(filePath);

					byte[] buf = new byte[1024];
					int bytesRead = input.read(buf);

					while (bytesRead != -1) {
						bos.write(buf, 0, bytesRead);
						bytesRead = input.read(buf);
					}
					bos.flush();

					byte[] data = bos.toByteArray();
					ByteArrayBody bab = new ByteArrayBody(data, description);

					reqEntity.addPart(FILE_PARAMETER, bab);

					postRequest.setEntity(reqEntity);
					HttpResponse response = httpClient.execute(postRequest);
					System.out.println(response.getStatusLine());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
