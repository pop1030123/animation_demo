package com.pop.anim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by pengfu on 15/12/27.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    Button bsfa ,bszi ,sd2u ,sl2r ,sr2l ,su2d ,tl ,tr ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bsfa = (Button)findViewById(R.id.black_square_fade_away) ;
        bszi = (Button)findViewById(R.id.black_square_zoom_in) ;
        sd2u = (Button)findViewById(R.id.shutter_down_2_up) ;
        sl2r = (Button)findViewById(R.id.shutter_left_2_right) ;
        sr2l = (Button)findViewById(R.id.shutter_right_2_left) ;
        su2d = (Button)findViewById(R.id.shutter_up_2_down) ;
        tl = (Button)findViewById(R.id.translate_left) ;
        tr = (Button)findViewById(R.id.translate_right) ;
        bsfa.setOnClickListener(this);
        bszi.setOnClickListener(this);
        sd2u.setOnClickListener(this);
        sl2r.setOnClickListener(this);
        sr2l.setOnClickListener(this);
        su2d.setOnClickListener(this);
        tl.setOnClickListener(this);
        tr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent toAnim = new Intent() ;
        toAnim.setClass(this ,SlideShowActivity.class) ;
        switch (v.getId()){
            case R.id.black_square_fade_away:
                toAnim.putExtra("type" ,0) ;
                break ;
            case R.id.black_square_zoom_in:
                toAnim.putExtra("type" ,1) ;
                break ;
            case R.id.shutter_down_2_up:
                toAnim.putExtra("type" ,2) ;
                break ;
            case R.id.shutter_left_2_right:
                toAnim.putExtra("type" ,3) ;
                break ;
            case R.id.shutter_right_2_left:
                toAnim.putExtra("type" ,4) ;
                break ;
            case R.id.shutter_up_2_down:
                toAnim.putExtra("type" ,5) ;
                break ;
            case R.id.translate_left:
                toAnim.putExtra("type" ,6) ;
                break ;
            case R.id.translate_right:
                toAnim.putExtra("type" ,7) ;
                break ;
        }
        startActivity(toAnim);
    }
}
