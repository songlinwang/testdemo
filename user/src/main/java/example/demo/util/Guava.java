package example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author wsl
 * @date 2018/12/28
 */
public class Guava {

    // 将 list中的数据 拼接成 用 , 分割的string
    public static void joinUtil() {
        List<String> strs = Lists.newArrayList("1", "2", "3"); // 生成以个list
        String joinResult = Joiner.on(",").skipNulls().join(strs);
        System.out.println(joinResult);
    }

    // 将key相同的value放到一个list里面  以前的写法是 if(contains(key)) list.add  else new List.add(key)
    // 直接交给multimap
    public static void multiMapUtil() {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        for (int i = 0; i < 20; i++) {
            multimap.put("map", i + "");
        }
        Set<String> keySet = multimap.keys().elementSet(); // 转化成正常的set集合
        for (String s : keySet) {
            System.out.println(s);
            System.out.println(JSONObject.toJSON(multimap.get(s)));
        }
    }

    public static void main(String[] args) {
        joinUtil();
        multiMapUtil();
        String spl = ",a,b ,";
        String[] ss = spl.split(",");
        for (int i = 0; i < ss.length; i++) {
            System.out.println("---");
            System.out.println(ss[i]);
        }
        Iterator<String> stringIterator = Splitter.on(',').trimResults().omitEmptyStrings().split(spl).iterator();
        while (stringIterator.hasNext()) {
            System.out.println("****");
            System.out.println(stringIterator.next());
        }

    }
}
