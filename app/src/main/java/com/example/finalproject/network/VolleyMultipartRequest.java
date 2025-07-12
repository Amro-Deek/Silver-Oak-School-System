package com.example.finalproject.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class VolleyMultipartRequest extends Request<NetworkResponse> {
    private final Response.Listener<NetworkResponse> mListener;
    private final Map<String, String> mHeaders;

    public VolleyMultipartRequest(int method, String url,
                                  Response.Listener<NetworkResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mHeaders = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // Add form data
            for (Map.Entry<String, String> entry : getParams().entrySet()) {
                bos.write(("--" + boundary + LINE_FEED).getBytes());
                bos.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_FEED).getBytes());
                bos.write((LINE_FEED).getBytes());
                bos.write((entry.getValue() + LINE_FEED).getBytes());
            }

            // Add file data
            Map<String, DataPart> data = getByteData();
            for (Map.Entry<String, DataPart> entry : data.entrySet()) {
                bos.write(("--" + boundary + LINE_FEED).getBytes());
                bos.write(("Content-Disposition: form-data; name=\"" +
                        entry.getKey() + "\"; filename=\"" +
                        entry.getValue().getFileName() + "\"" + LINE_FEED).getBytes());
                bos.write(("Content-Type: " +
                        entry.getValue().getType() + LINE_FEED).getBytes());
                bos.write((LINE_FEED).getBytes());

                bos.write(entry.getValue().getContent());
                bos.write((LINE_FEED).getBytes());
            }

            bos.write(("--" + boundary + "--" + LINE_FEED).getBytes());

            return bos.toByteArray();
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return null;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    protected abstract Map<String, DataPart> getByteData() throws AuthFailureError;

    protected Map<String, String> getParams() throws AuthFailureError {
        return new HashMap<>();
    }

    public static class DataPart {
        private final String fileName;
        private final byte[] content;
        private final String type;

        public DataPart(String name, byte[] data) {
            fileName = name;
            content = data;
            type = "application/octet-stream";
        }

        public DataPart(String name, byte[] data, String mimeType) {
            fileName = name;
            content = data;
            type = mimeType;
        }

        public String getFileName() {
            return fileName;
        }

        public byte[] getContent() {
            return content;
        }

        public String getType() {
            return type;
        }
    }

    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private static final String LINE_FEED = "\r\n";
}
