package com.zhihuianxin.xyaxf.app.banner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.axinfu.basetools.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.zhihuianxin.axutil.Util;
import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.banner.banner.XBanner;
import com.zhihuianxin.xyaxf.modle.base.thrift.business.Business;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.ecard.ECardAccount;
import com.zhihuianxin.xyaxf.modle.base.thrift.fee.FeeAccount;
import com.zhihuianxin.xyaxf.modle.base.thrift.message.ActionType;
import com.zhihuianxin.xyaxf.modle.base.thrift.message.Advertise;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by zcrpro on 16/9/29.
 */

public class BannerFragment extends BaseFragment implements XBanner.XBannerAdapter {
    public static final String EXTRA_DATA = "extra_data";
    @InjectView(R.id.xbanner)
    XBanner xbanner;

    private List<Advertise> imgesUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.banner_fragment;
    }


    public void loadBanner(List<Advertise> list) {
        if (list != null) {
            boolean has = false;
            for (Advertise obj : list) {
                if (obj.title.equals("本地学校首页")) {
                    has = true;
                }
            }
            if (!has) {
                Advertise advertise = new Advertise();
                advertise.title = "本地学校首页";
                list.add(advertise);
            }
            imgesUrl.clear();
            imgesUrl.addAll(changeListOrder(list));
            xbanner.setData(imgesUrl, null);
        } else {
            xbanner = null;
        }
    }

    // 学校本地首页保持banner第一个
    private List<Advertise> changeListOrder(List<Advertise> list) {
        ArrayList arrayList = new ArrayList();
        Advertise firstAD = null;
        Iterator<Advertise> it = list.iterator();
        while (it.hasNext()) {
            Advertise advertise = it.next();
            if (advertise.title.equals("本地学校首页")) {
                firstAD = advertise;
                it.remove();
            }
        }

        if (firstAD == null) {
            if (list.size() == 0) {
                Advertise advertise = new Advertise();
                advertise.title = "本地学校首页";
                arrayList.add(advertise);
                return arrayList;
            }else {
                return list;
            }
        } else {
            if (list.size() == 0) {
                arrayList.add(firstAD); //todo 完善去除广告加入学校地标的逻辑
            }
            arrayList.addAll(list);
            return arrayList;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        imgesUrl = new ArrayList<>();
        xbanner.setmAdapter(this);
        loadBanner((List<Advertise>) getArguments().getSerializable(EXTRA_DATA));
        //xbanner = (XBanner) rootView.findViewById(R.id.xbanner);
        xbanner.setPageChangeDuration(1500);
        xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Advertise advertise = imgesUrl.get(position);
                if (advertise == null || advertise.action == null || Util.isEmpty(advertise.action.args)) {
                    return;
                }

                final String args = advertise.action.args;
                if (advertise.action.type.equals(ActionType.ShowText.name())) {
                    Intent intent = new Intent(getActivity(), BannerDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BannerDetailActivity.EXTRA_DATA, advertise);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (advertise.action.type.equals(ActionType.ShowText.OpenURL.name())) {
//                    Intent intent = new Intent(getActivity(), WebPageActivity.class);
//                    try {
//                        intent.putExtra(WebPageActivity.EXTRA_URL, new JSONObject(args).getString("url"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    getActivity().startActivity(intent);
                } else if (advertise.action.type.equals(ActionType.ShowText.OpenBusiness.name())) {
                    String businessNo;
                    try {
                        businessNo = new JSONObject(args).getString("business_no");
//                        goBusiness(businessNo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Node do nothing.
                }
            }
        });
        return rootView;
    }

//    private void goBusiness(String no) {
//        RealmResults<Business> realmResults = realm.where(Business.class).findAll();
//        for (Business obj : realmResults) {
//            if (Util.isEmpty(no) || Util.isEmpty(((BusinessRealmProxy) obj).realmGet$no()) || !no.equals(((BusinessRealmProxy) obj).realmGet$no())) {
//                continue;
//            }
//
//            if (((BusinessRealmProxy) obj).realmGet$type().equals("Fee")) {
//                final RealmResults<Customer> customers = realm.where(Customer.class).equalTo("mobile", App.mAxLoginSp.getUserMobil()).findAll();
//                final FeeAccount feeAccount = ((CustomerRealmProxy) customers.get(0)).realmGet$fee_account();
//                if (((FeeAccountRealmProxy) feeAccount).realmGet$status().equals("OK")) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(FeeActivity.ENTER_FLAG, FeeActivity.normal);
//                    Intent intent = new Intent(getActivity(), FeeActivity.class);
//                    intent.putExtras(bundle);
//                    getActivity().startActivity(intent);
//                } else {
//                    getActivity().startActivity(new Intent(getActivity(), FeeCheckActivity.class));
//                }
//
//            } else if (((BusinessRealmProxy) obj).realmGet$type().equals("ECard")) {
//                final RealmResults<Customer> customers = realm.where(Customer.class).equalTo("mobile", App.mAxLoginSp.getUserMobil()).findAll();
//                final ECardAccount eCardAccount = ((CustomerRealmProxy) customers.get(0)).realmGet$ecard_account();
//                if (((ECardAccountRealmProxy) eCardAccount).realmGet$status().equals("OK")) {
//                    getActivity().startActivity(new Intent(getActivity(), EcardActivity.class));
//                } else {
//                    getActivity().startActivity(new Intent(getActivity(), EcardOpenActivity.class));
//                }
//            } else if (((BusinessRealmProxy) obj).realmGet$type().equals("Web")) {
//                Intent intent = new Intent(getActivity(), WebPageActivity.class);
//                intent.putExtra(WebPageActivity.EXTRA_URL, ((BusinessRealmProxy) obj).realmGet$arg());
//                intent.putExtra(WebPageActivity.EXTRA_TITLE, ((BusinessRealmProxy) obj).realmGet$title());
//                getActivity().startActivity(intent);
//            } else {// TdtcFee MobileRecharge MobileFlow Next Version.
//                Toast.makeText(getActivity(), "此功能暂未开通", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void loadBanner(XBanner banner, View view, int position) {
        if (imgesUrl.get(position).title.equals("本地学校首页")) {
            //本地文件
            File file = new File(Environment.getExternalStorageDirectory(), App.mAxLoginSp.getLadmarkName());
            //加载图片
            Glide.with(this).load(file).into((ImageView) view);
        } else {
            if (Util.isEmpty(imgesUrl.get(position).image)) {
                Glide.with(this).load(R.drawable.defaulf9_sq).into((ImageView) view);
            } else {
                Glide.with(this).load(imgesUrl.get(position).image).into((ImageView) view);
            }
        }
    }
}
