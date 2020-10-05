package com.example.recipes.Shouye;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.recipes.R;
import com.example.recipes.Rank.RankActivity;
import com.example.recipes.Recent.RecentActivity;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesContentActivity;
import com.example.recipes.Recipes.RecipesDAO;
import com.example.recipes.classify.ClassifyActivity;
import com.example.recipes.share.SearchActivity;

import java.util.List;

/**
 * Created by xjh on 2018/7/10.
 */

public class ShouyeFragment extends Fragment {

    private String uri = "content://media/external/images/media/646";

    private List<Recipes> recipesList;
    private ListView lv;
    private RecipesAdapter adapter;

    private Button cancel_edit_bt;
    private EditText search_ed;
    private ImageView newrecipes;
    private ImageView ranking;
    private ImageView sort;
    private TextView newrecipes_tv, ranking_tv, sort_tv;
    private ImageSwitcher imageSwitcher;
    private int index;
    private int[] img;

    Handler handler;
    float mPosX = 0, mCurPosx = 0;
    Runnable r;
    View view;


    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shouye, container, false);

        cancel_edit_bt = (Button) view.findViewById(R.id.cancel_bt);
        search_ed = (EditText) view.findViewById(R.id.search_et);
        newrecipes = (ImageView) view.findViewById(R.id.newrecipes_iv);
        ranking = (ImageView) view.findViewById(R.id.ranking_iv);
        sort = (ImageView) view.findViewById(R.id.sort_iv);
        newrecipes_tv = (TextView) view.findViewById(R.id.newrecipes_tv);
        ranking_tv = (TextView) view.findViewById(R.id.ranking_tv);
        sort_tv = (TextView) view.findViewById(R.id.sort_tv);
        lv = (ListView) view.findViewById(R.id.recipe_lv);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.cancel_bt:
                        //取消搜索
                        hideInputMethod(getActivity(), v);
                        break;
                    case R.id.newrecipes_iv:
                    case R.id.newrecipes_tv:
                        //发现新菜谱,跳转发现新菜页
                        intent = new Intent(getActivity(), RecentActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.ranking_iv:
                    case R.id.ranking_tv:
                        //跳转排行页
                        intent = new Intent(getActivity(), RankActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.sort_iv:
                    case R.id.sort_tv:
                        //跳转分类页
                        intent = new Intent(getActivity(), ClassifyActivity.class);
                        startActivity(intent
                        );
                        break;
                }
            }
        };

        cancel_edit_bt.setOnClickListener(clickListener);
        newrecipes.setOnClickListener(clickListener);
        ranking.setOnClickListener(clickListener);
        sort.setOnClickListener(clickListener);
        newrecipes_tv.setOnClickListener(clickListener);
        ranking_tv.setOnClickListener(clickListener);
        sort_tv.setOnClickListener(clickListener);

        //        search_ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        //            @Override
        //            public void onFocusChange(View v, boolean hasFocus) {
        //                if (hasFocus) {
        //                    Intent intent = new Intent(getActivity(), SearchActivity.class);
        //                    startActivity(intent);
        //                }
        //            }
        //        });

        search_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                //                linkrecipes_name.setText(str);
                //在该改变事件里调用查询函数，可以即输即查
                if (!str.equals("")) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("search", str);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 110);
                } else {
                    //                    clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        init();
        adapter = new RecipesAdapter(getActivity(), recipesList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipes recipes = recipesList.get(position);
                Intent intent = new Intent(getActivity(), RecipesContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipes", recipes);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        //图片轮播
        imageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageSwitch);
        index = 0;
        img = new int[]{
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3
        };
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getActivity());
                imageView.setBackgroundColor(0xFFFFFFFF);  //白色背景
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));    //定义布局
                return imageView;
            }
        });
        imageSwitcher.setImageResource(img[0]);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));


        mPosX = 0;
        mCurPosx = 0;

        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                index++;
                if (index >= img.length) {
                    index = 0;
                }
                imageSwitcher.setImageResource(img[index]);
                handler.postDelayed(this, 2500);
            }
        };
        handler.postDelayed(r, 500);

        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        handler.removeCallbacks(r);
                        break;
                    case MotionEvent.ACTION_UP:
                        mCurPosx = event.getX();
                        if ((mCurPosx - mPosX) > 0) {//左滑
                            index--;
                            if (index < 0) {
                                index = img.length - 1;
                            }
                        }
                        if ((mPosX - mCurPosx) > 0) {//右滑
                            index++;
                            if (index >= img.length) {
                                index = 0;
                            }
                        }
                        imageSwitcher.setImageResource(img[index]);
                        handler.postDelayed(r, 100);
                        break;
                }
                return true;
            }
        };
        imageSwitcher.setOnTouchListener(listener);

        return view;
    }

    //取消输入法并清空搜索编辑框
    private void hideInputMethod(Context context, View view) {
        //清空编辑框
        search_ed.getText().clear();
        //关闭输入法
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void init() {

        RecipesDAO recipesDAO = new RecipesDAO(getActivity());
        recipesList = recipesDAO.get();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 110) {
            hideInputMethod(getActivity(), view);
        }
    }
}
