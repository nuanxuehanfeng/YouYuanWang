package com.bawei.wangxueshi.youyuanwang.fragment.fragmentbuttom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawei.wangxueshi.youyuanwang.R;
import com.bawei.wangxueshi.youyuanwang.activity.MeFragmentSetActivity;
import com.bawei.wangxueshi.youyuanwang.activity.MePhotoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/10.
 */

public class MeFragment extends Fragment {


    @BindView(R.id.f_me_iv_head)
    ImageView fMeIvHead;
    @BindView(R.id.f_me_nickname)
    TextView fMeNickname;
    @BindView(R.id.f_me_linear_head)
    LinearLayout fMeLinearHead;
    @BindView(R.id.f_me_linear_me)
    LinearLayout fMeLinearMe;
    @BindView(R.id.f_me_linear_chengxin)
    LinearLayout fMeLinearChengxin;
    @BindView(R.id.f_me_linear_photo)
    LinearLayout fMeLinearPhoto;
    @BindView(R.id.f_me_linear_action)
    LinearLayout fMeLinearAction;
    @BindView(R.id.f_me_linear_friends)
    LinearLayout fMeLinearFriends;
    @BindView(R.id.f_me_linear_set)
    LinearLayout fMeLinearSet;
    @BindView(R.id.f_me_linear_vip)
    LinearLayout fMeLinearVip;
    @BindView(R.id.f_me_linear_service)
    LinearLayout fMeLinearService;
    @BindView(R.id.f_me_linear_newperson)
    LinearLayout fMeLinearNewperson;
    @BindView(R.id.f_me_linear_help)
    LinearLayout fMeLinearHelp;
    @BindView(R.id.f_me_linear_migu)
    LinearLayout fMeLinearMigu;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_me, null);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
    @OnClick({R.id.f_me_iv_head, R.id.f_me_linear_head, R.id.f_me_linear_me, R.id.f_me_linear_chengxin, R.id.f_me_linear_photo, R.id.f_me_linear_action, R.id.f_me_linear_friends, R.id.f_me_linear_set, R.id.f_me_linear_vip, R.id.f_me_linear_service, R.id.f_me_linear_newperson, R.id.f_me_linear_help, R.id.f_me_linear_migu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.f_me_iv_head:
                break;
            case R.id.f_me_linear_head:
                break;
            case R.id.f_me_linear_me:
                break;
            case R.id.f_me_linear_chengxin:
                break;
            case R.id.f_me_linear_photo:
                startActivity(new Intent(getActivity(), MePhotoActivity.class));
                break;
            case R.id.f_me_linear_action:
                break;
            case R.id.f_me_linear_friends:
                break;
            case R.id.f_me_linear_set:
                startActivity(new Intent(getActivity(), MeFragmentSetActivity.class));

                break;
            case R.id.f_me_linear_vip:
                break;
            case R.id.f_me_linear_service:
                break;
            case R.id.f_me_linear_newperson:
                break;
            case R.id.f_me_linear_help:
                break;
            case R.id.f_me_linear_migu:
                break;
        }
    }




}
