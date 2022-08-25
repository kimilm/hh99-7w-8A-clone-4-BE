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
            "https://chestercathedral.com/wp-content/uploads/2016/04/The-Snowman-200px-x-200px.jpg",
            "https://img1.daumcdn.net/thumb/R300x0/?fname=https://k.kakaocdn.net/dn/nH8qj/btrlpLBr8zp/cAhq7EOkImBrR1qKn6tKk0/img.jpg",
            "https://user-images.githubusercontent.com/15075501/186451630-c74cd545-e27c-4e44-b740-acc10bf391e8.png",
            "https://user-images.githubusercontent.com/15075501/186451862-c2189cb2-0fcb-4d67-af33-fc0917a7b719.png",
            "https://static-cdn.jtvnw.net/jtv_user_pictures/371fe9ee-7da7-4132-8d12-01dd54b77169-profile_image-300x300.png",
            "https://user-images.githubusercontent.com/15075501/186452449-ecbda4b7-ea2f-4e36-8ab2-50f71594fbd5.png",
            "https://user-images.githubusercontent.com/15075501/186452615-c0f46291-eb9b-4908-9db7-a8fab5ce3004.png"
    };

    private static final String[] DEFAULT_MEMBER_INTRODUCE = {
            "자 이제 시작이야",
            "내꿈을 위한 여행 피카츄",
            "걱정따윈 없어",
            "내친구랑 함께니까",
            "처음 시작은",
            "어색할지도 몰라",
            "몰라 내친구 피카츄",
            "날 지켜줄거라고 믿고있어",
            "누구라도 얕보다간 큰일나",
            "언제나 어디서나",
            "피카츄가 옆에있어",
            "약할때나 강할때나",
            "피카츄가 옆에있어",
            "너와 나 함께라면",
            "우린 최고야",
            "언제 언제까지나",
            "진실한 마음으로",
            "언제 언제까지나",
            "그날을 위해",
            "피카츄",
            "피카츄 라이츄 파이리 꼬부기",
            "버터플 야도란 피존투 또가스",
            "서로 생긴 모습은 달라도",
            "우리는 모두 친구 맞아",
            "산에서 들에서 때리고 뒹굴고",
            "사막에서 정글에서 울다가 웃다가",
            "서로 만나기까지 힘들었어도",
            "우리는 모두 친구 피카피카",
            "울랄랄라",
            "내가 원하는 걸 너도 원하고",
            "마주잡은 두 손에 맹세해",
            "힘을 내봐 그래 힘을 내봐",
            "용기를 내봐 그래 용기를 내봐",
            "피카피카 피카츄",
            "피카피카 피카츄",
            "아름다운 우리 추억 기억해",
            "만날 수 없어 만나고 싶은데",
            "그런 슬픈 기분인 걸",
            "말할 수 없어 말하고 싶은데",
            "속 마음만 들키는 걸",
            "내 사랑에 마법의 열쇠가 있다면",
            "그건 바로 이 세상이 아름다운 이유",
            "catch you catch you",
            "catch me catch me",
            "이젠 숨박꼭질은 그만(그만)",
            "우울한 건 모두",
            "파란하늘에 묻어버려",
            "오늘도 너에게 달려가는 이 마음",
            "나는 정말 정말",
            "너를 좋아해"
    };

    public static String getRandomMemberPic() {
        return DEFAULT_PROFILE_PICS[random.nextInt(DEFAULT_PROFILE_PICS.length)];
    }

    public static String getRandomRoomPic() {
        return DEFAULT_ROOM_PICS[random.nextInt(DEFAULT_ROOM_PICS.length)];
    }

    public static String getRandomIntroduce() {
        return DEFAULT_MEMBER_INTRODUCE[random.nextInt(DEFAULT_MEMBER_INTRODUCE.length)];
    }
}


