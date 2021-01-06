package com.khalej.direct.model;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface apiinterface_home {

    @FormUrlEncoded
    @POST("montag/direkt/api/login")
    Call<contact_userinfo> getcontacts_login(@Field("kayWord") String phone, @Field("password") String password);


    @FormUrlEncoded
    @POST("montag/direkt/api/canRest")
    Call<Reset>getcontacts_ResetPassword(@Field("kayWord") String kayWord);

    @GET("montag/direkt/api/all_slider")
    Call<List<contact_annonce>> getcontacts_annonce();
    @GET("montag/direkt/api/get_all_categories")
    Call<List<contact_home>> getcontacts_first();
    @GET("montag/direkt/api/get_all_companies")
    Call<List<Companys>> getcontacts_Companys();
    @FormUrlEncoded
    @POST("montag/direkt/api/contact_us")
    Call<ResponseBody> CallUs(@Field("name") String name, @Field("email") String address,
                                              @Field("subject") String subject,  @Field("message") String message);


    @GET("montag/direkt/api/aboutUs")
    Call<AboutUs> AboutUS();

    @GET("montag/direkt/api/condtions")
    Call<AboutUs> Conditoins();
    @GET("montag/direkt/api/cities")
    Call<List<Citys>> Citys();

    @FormUrlEncoded
    @POST("montag/direkt/api/single_room")
    Call<List<ChatMessage>>getcontacts_chat(@Field("room_id")int room_id,@Field("user_id")int user_id);
    @FormUrlEncoded
    @POST("montag/direkt/api/my_notification")
    Call<List<notificationData>>getcontacts_Notification(@Field("user_id")int user_id);


    @FormUrlEncoded
    @POST("montag/direkt/api/send_message")
    Call<chatRoom>getcontacts_SendMessage(@Field("admin_id")int admin_id,@Field("user_id")int user_id,
                                              @Field("message")String message);


    @FormUrlEncoded
    @POST("montag/direkt/api/add_order")
    Call<ResponseBody> getcontacts_order(@Field("user_id")int id,@Field("category_id")int category_id,
                                         @Field("amount")int numOfSets, @Field("latitude_from")double latFrom,
         @Field("longitude_from")double lngFroxm , @Field("latitude_to")double latTo, @Field("longitude_to")double lngTo,
             @Field("address_from")String locationFromAddress,
              @Field("address_to")String locationToAddress ,@Field("des")String details, @Field("type") String type ,
                                         @Field("days") String days,@Field("mark") String mark);

    @FormUrlEncoded
    @POST("montag/direkt/api/my_orders")
    Call<List<Orders>> getcontacts_MyOrders(@Field("user_id")int id,@Field("type")int type);
    @FormUrlEncoded
    @POST("montag/direkt/api/my_orders")
    Call<List<Order>> getcontacts_MyOrder(@Field("user_id")int id,@Field("type")int type);
    @Multipart
    @POST("montag/direkt/api/register")
    Call<contact_userinfo> getcontacts_newaccount(@Part MultipartBody.Part image, @Part("ff") RequestBody img, @Part("name") RequestBody name, @Part("password") RequestBody password, @Part("email") RequestBody address,
                                              @Part("phone") RequestBody phone, @Part("type")RequestBody type);

    @Multipart
    @POST("montag/direkt/api/register")
    Call<ResponseBody> getcontacts_newaccountCar(@Part MultipartBody.Part image,@Part("name") RequestBody name, @Part("password") RequestBody password, @Part("email") RequestBody address,
                                              @Part("phone") RequestBody phone , @Part("car_number") RequestBody car_number,@Part("car_model") RequestBody car_model,
                                                 @Part MultipartBody.Part image_id,@Part MultipartBody.Part image_car,@Part("city") RequestBody city
                                                 ,@Part("type")RequestBody type ,@Part("latitude")RequestBody lat,@Part("longitude") RequestBody lng);

    @Multipart
    @POST("montag/direkt/api/register")
    Call<ResponseBody> getcontacts_newaccountFany(@Part MultipartBody.Part image,@Part MultipartBody.Part image2,@Part MultipartBody.Part image3,@Part("name") RequestBody name, @Part("password") RequestBody password, @Part("email") RequestBody address,
                                                  @Part("phone") RequestBody phone, @Part("city") RequestBody city,@Part("service") RequestBody service,
                                                  @Part("type")RequestBody type,@Part("latitude")RequestBody lat,@Part("longitude") RequestBody lng);

    @Multipart
    @POST("montag/direkt/api/add_order")
    Call<ResponseBody> getcontacts_order_Image(@Part MultipartBody.Part image,@Part("user_id")RequestBody id,@Part("category_id")RequestBody category_id,
                                         @Part("amount")RequestBody numOfSets, @Part("latitude_from")RequestBody latFrom,
                                         @Part("longitude_from")RequestBody lngFroxm , @Part("latitude_to")RequestBody latTo, @Part("longitude_to")RequestBody lngTo,
                                         @Part("address_from")RequestBody locationFromAddress,
                                         @Part("address_to")RequestBody locationToAddress ,@Part("des")RequestBody details, @Part("type") RequestBody type ,
                                         @Part("days") RequestBody days,@Part("mark") RequestBody mark);




}

