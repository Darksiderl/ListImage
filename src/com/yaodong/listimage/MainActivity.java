package com.yaodong.listimage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.adapters.ImageListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private List<String> imageUrls;
    private ListView listView;
    private ImageListAdapter ILAdapter;
    private String[]  imageStrs = {
            "http://h.hiphotos.baidu.com/image/h%3D360/sign=168aec7ead014c08063b2ea33a79025b/359b033b5bb5c9eaea606234d139b6003bf3b352.jpg",
            "http://b.hiphotos.baidu.com/image/h%3D360/sign=4966caee48086e0675a8394d320a7b5a/023b5bb5c9ea15cec72cb6d6b2003af33b87b22b.jpg",
            "http://b.hiphotos.baidu.com/image/h%3D360/sign=dc77d7147f899e51678e3c1272a6d990/e824b899a9014c08878b2c4c0e7b02087af4f4a3.jpg",
            "http://c.hiphotos.baidu.com/image/h%3D360/sign=cbe28f3a6209c93d18f208f1af3cf8bb/aa64034f78f0f736ad3689350f55b319ebc4133d.jpg",
            "http://h.hiphotos.baidu.com/image/h%3D360/sign=0864d23db08f8c54fcd3c3290a282dee/c9fcc3cec3fdfc033570f76bd03f8794a5c226dc.jpg",
            "http://g.hiphotos.baidu.com/image/h%3D360/sign=74e33ec80d24ab18ff16e73105fbe69a/86d6277f9e2f07080ebdf1eeed24b899a801f2a0.jpg",
            " http://f.hiphotos.baidu.com/image/h%3D360/sign=b9320fe619950a7b6a3548c23ad0625c/c8ea15ce36d3d539433a5b133e87e950342ab0df.jpg",
            " http://a.hiphotos.baidu.com/image/h%3D360/sign=ad6dfc5bb6b7d0a264c9029bfbee760d/b2de9c82d158ccbf6cd825d31dd8bc3eb03541f6.jpg",
            " http://d.hiphotos.baidu.com/image/h%3D360/sign=a70969ff3e292df588c3aa138c335ce2/9345d688d43f87949725e4fed61b0ef41ad53a03.jpg",
            " http://d.hiphotos.baidu.com/image/h%3D360/sign=fadedc1c8e1363270aedc435a18ea056/11385343fbf2b2117812c58ece8065380cd78e36.jpg",
            "http://d.hiphotos.baidu.com/image/h%3D360/sign=fd73dc65bd014a909e3e40bb99763971/21a4462309f790527b4624e908f3d7ca7acbd588.jpg",
            " http://d.hiphotos.baidu.com/image/h%3D360/sign=d764ebb390eef01f52141ec3d0fc99e0/c2fdfc039245d688b7d4e073a0c27d1ed31b2416.jpg",
            "http://g.hiphotos.baidu.com/image/h%3D360/sign=ebfaa6eb0af41bd5c553eef261d881a0/f9198618367adab4dc095a838fd4b31c8601e411.jpg",
            " http://h.hiphotos.baidu.com/image/h%3D360/sign=fcf102f94f36acaf46e090fa4cd88d03/18d8bc3eb13533fab595d2eeacd3fd1f40345bca.jpg",
            "http://h.hiphotos.baidu.com/image/h%3D360/sign=a88eefe474cf3bc7f700cbeae101babd/79f0f736afc37931c5d295c0efc4b74542a911a4.jpg",
            "http://g.hiphotos.baidu.com/image/h%3D360/sign=c7fd97e3bc0e7bec3cda05e71f2cb9fa/960a304e251f95ca2f34115acd177f3e6609521d.jpg"
            };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.listView);
        imageUrls = new ArrayList<>();
        for (int i = 0; i < imageStrs.length; i++) {
            imageUrls.add(imageStrs[i]);
        }
        ILAdapter = new ImageListAdapter(this,imageUrls);
        listView.setAdapter(ILAdapter);
    }
}
