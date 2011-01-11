package com.adakoda.android.googleurlshortersample;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GoogleURLShorterSampleActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String apiUri = "https://www.googleapis.com/urlshortener/v1/url";
		// �ȉ��� API Key ���擾�������̂ɒu��������i�ȗ��j
		String apiKey = "";
		String postUrl = ""; // POST�pURL������

		// �Z�k��URL������
		String longUrl = "http://www.adakoda.com/";

		// �p�����[�^�[�ɓ��{����܂ޏꍇ�͉��L�̂悤�ɃG�X�P�[�v���Ă�������
		// Uri.Builder tmpUriBuilder = new Uri.Builder();
		// tmpUriBuilder("http://www.google.co.jp/search");
		// tmpUriBuilder.appendQueryParameter("q", Uri.encode("�݂����݂�"));
		// longUrl = Uri.decode(tmpUriBuilder.build().toString());

		// POST�pURL������쐬
		Uri.Builder uriBuilder = new Uri.Builder();
		uriBuilder.path(apiUri);
		uriBuilder.appendQueryParameter("key", apiKey); // API�L�[����
		postUrl = Uri.decode(uriBuilder.build().toString());

		try {
			// ���N�G�X�g�쐬
			HttpPost httpPost = new HttpPost(postUrl);
			httpPost.setHeader("Content-type", "application/json");
			JSONObject jsonRequest = new JSONObject();
			jsonRequest.put("longUrl", longUrl);
			StringEntity stringEntity = new StringEntity(jsonRequest.toString());
			httpPost.setEntity(stringEntity);
			// ���N�G�X�g���s
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
			HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				// ���ʂ̎擾
				String entity = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonEntity = new JSONObject(entity);
				if (jsonEntity != null) {
					// �Z�kURL���� �i���̃T���v���̏ꍇ�A�uhttp://goo.gl/sGdK�v�j
					String shortUrl = jsonEntity.optString("id");
					Log.v("id", shortUrl);
					Toast.makeText(this, shortUrl, Toast.LENGTH_LONG).show();
				}
			}
		} catch (IOException e) {
		} catch (JSONException e) {
		}
	}
}