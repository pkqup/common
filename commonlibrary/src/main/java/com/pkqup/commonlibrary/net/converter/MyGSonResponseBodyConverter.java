package com.pkqup.commonlibrary.net.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.pkqup.commonlibrary.net.bean.ResultBean;
import com.pkqup.commonlibrary.net.exception.ApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;


public class MyGSonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public MyGSonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        ResultBean re = gson.fromJson(response, ResultBean.class);
        if (re.getErrorcode() != 0) {
            value.close();
            throw new ApiException(re.getMsg(), re.getErrorcode());
        }

        MediaType mediaType = value.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(bis, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
