package leetcode;

public class Test {


    public static void main(String[] args) {
        System.out.println(prin(""));
    }

    public static int prin(String s){
        String str = s;
        int len = str.length();
        String str_now = "";
        String str0 = "";
        String str1 = "";
        String str2 = "";

        String str_zc = "";
        int len_zc = 0;

        int cd = len;
        while(cd >= 0) {
            for (int i = 0; i < cd; i++) {
                str_now = str.substring(i, cd);
                for (int j = 0; j < str_now.length(); j++) {
                    str0 = str_now.substring(0, j);
                    str1 = str_now.substring(j, j + 1);
                    str2 = str_now.substring(j+1);
                    if (str0.contains(str1) || str2.contains(str1)) {
                        break;
                    }
                    if (j==str_now.length()-1){
                        if (str_now.length()>len_zc){
                            len_zc = str_now.length();
                            str_zc = str_now;
                        }
                    }
                }
            }
            cd--;
        }
        System.out.println(str_zc+":是最长无重复子串，长度："+len_zc);
        return len_zc;
    }
}
