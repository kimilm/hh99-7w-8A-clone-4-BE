package be.clone.kakao.util;

import java.util.Random;

public class MemberUtils {

    private static final Random random = new Random(System.currentTimeMillis());

    private static final String[] DEFAULT_PROFILE_PICS = {
            "https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/309/59932b0eb046f9fa3e063b8875032edd_crop.jpeg",
            "https://img2.daumcdn.net/thumb/R158x158/?fname=http%3A//twg.tset.daumcdn.net/profile/7nuPvrFxTag0%3Ft=20220821142244",
            "https://accounts.kakao.com/assets/weblogin/img_profile.png"
    };

    public static String getPic() {
        return DEFAULT_PROFILE_PICS[random.nextInt(DEFAULT_PROFILE_PICS.length)];
    }
}


