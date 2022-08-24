package be.clone.kakao.util;

import java.util.Random;

public class KakaoUtils {

    private static final Random random = new Random(System.currentTimeMillis());

    private static final String[] DEFAULT_PROFILE_PICS = {
            "https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/309/59932b0eb046f9fa3e063b8875032edd_crop.jpeg",
            "https://img2.daumcdn.net/thumb/R158x158/?fname=http%3A//twg.tset.daumcdn.net/profile/7nuPvrFxTag0%3Ft=20220821142244",
            "https://accounts.kakao.com/assets/weblogin/img_profile.png",
            "https://media.istockphoto.com/vectors/mustache-icon-flat-web-pictogram-or-sign-isolated-on-grey-background-vector-id1175915187?k=20&m=1175915187&s=170667a&w=0&h=YkiE0arkfZ5pJfiTPYI1jZFLw-8Y1AQr2V9Pe5U9JJQ=",
            "https://user-images.githubusercontent.com/15075501/186417883-578fcfe2-39d0-4af8-9384-f91f99265ad3.png",
            "https://img1.daumcdn.net/thumb/C176x176/?fname=https://k.kakaocdn.net/dn/bJcsE9/btq5MnYuK4N/k3akMAFLyfj9Sxh33R2EnK/img.png",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7dEBGob06vbYnOmEIFGQCHxSz1h_YV2WQCA&usqp=CAU",
            "https://user-images.githubusercontent.com/15075501/186452894-24fb9f40-7b83-4ee4-837d-b158258332c0.png"
    };

    private static final String[] DEFAULT_ROOM_PICS = {
            "https://i0.wp.com/forhappywomen.com/wp-content/uploads/2018/11/%EC%82%B0%EB%B6%80%EC%9D%B8%EA%B3%BC-%ED%8F%AC%ED%95%B4%ED%94%BC%EC%9A%B0%EB%A8%BC-%EB%AC%B8%EC%9D%98-%EC%B9%B4%EC%B9%B4%EC%98%A4%ED%94%8C%EB%9F%AC%EC%8A%A4%EC%B9%9C%EA%B5%AC-%EB%B2%84%ED%8A%BC.png?w=300&ssl=1",
            "https://chestercathedral.com/wp-content/uploads/2016/04/The-Snowman-200px-x-200px.jpg",
            "https://img1.daumcdn.net/thumb/R300x0/?fname=https://k.kakaocdn.net/dn/nH8qj/btrlpLBr8zp/cAhq7EOkImBrR1qKn6tKk0/img.jpg",
            "https://user-images.githubusercontent.com/15075501/186451630-c74cd545-e27c-4e44-b740-acc10bf391e8.png",
            "https://user-images.githubusercontent.com/15075501/186451862-c2189cb2-0fcb-4d67-af33-fc0917a7b719.png",
            "https://static-cdn.jtvnw.net/jtv_user_pictures/371fe9ee-7da7-4132-8d12-01dd54b77169-profile_image-300x300.png",
            "https://user-images.githubusercontent.com/15075501/186452449-ecbda4b7-ea2f-4e36-8ab2-50f71594fbd5.png",
            "https://user-images.githubusercontent.com/15075501/186452615-c0f46291-eb9b-4908-9db7-a8fab5ce3004.png"
    };

    public static String getRandomMemberPic() {
        return DEFAULT_PROFILE_PICS[random.nextInt(DEFAULT_PROFILE_PICS.length)];
    }

    public static String getRandomRoomPic() {
        return DEFAULT_ROOM_PICS[random.nextInt(DEFAULT_ROOM_PICS.length)];
    }
}


