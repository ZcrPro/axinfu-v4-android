package com.zhihuianxin.xyaxf.app.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.axinfu.basetools.base.BaseFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zhihuianxin.axutil.Util;
import com.zhihuianxin.xyaxf.App;
import com.zhihuianxin.xyaxf.R;
import com.zhihuianxin.xyaxf.app.banner.BannerFragment;
import com.zhihuianxin.xyaxf.app.home.contract.HomeContract;
import com.zhihuianxin.xyaxf.app.home.presenter.HomePresenter;
import com.zhihuianxin.xyaxf.modle.base.thrift.customer.Customer;
import com.zhihuianxin.xyaxf.modle.base.thrift.message.Advertise;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeFragment extends BaseFragment implements HomeContract.HomeView {

    public static final String EXTRA_TEXT = "extra_text";
    @InjectView(R.id.sun_def)
    ImageView sunDef;
    @InjectView(R.id.plant4_def)
    ImageView plant4Def;
    @InjectView(R.id.plant3_def)
    ImageView plant3Def;
    @InjectView(R.id.local_def)
    View localDef;
    @InjectView(R.id.ani3_def)
    RelativeLayout ani3Def;
    @InjectView(R.id.loufang_def)
    ImageView loufangDef;
    @InjectView(R.id.plant_def)
    ImageView plantDef;
    @InjectView(R.id.tree_view_def)
    RelativeLayout treeViewDef;
    @InjectView(R.id.banner_default_view)
    FrameLayout bannerDefaultView;
    @InjectView(R.id.banner_view)
    FrameLayout bannerView;
    @InjectView(R.id.ani5)
    ImageView ani5;
    @InjectView(R.id.sun)
    ImageView sun;
    @InjectView(R.id.building)
    ImageView building;
    @InjectView(R.id.plant4)
    ImageView plant4;
    @InjectView(R.id.plant3)
    ImageView plant3;
    @InjectView(R.id.local)
    View local;
    @InjectView(R.id.ani3)
    RelativeLayout ani3;
    @InjectView(R.id.local1)
    View local1;
    @InjectView(R.id.ani4)
    RelativeLayout ani4;
    @InjectView(R.id.loufang)
    ImageView loufang;
    @InjectView(R.id.plant)
    ImageView plant;
    @InjectView(R.id.tree_view)
    RelativeLayout treeView;
    @InjectView(R.id.fl_banner_bg)
    FrameLayout flBannerBg;
    @InjectView(R.id.banner)
    FrameLayout banner;
    @InjectView(R.id.home_qr)
    ImageView homeQr;
    @InjectView(R.id.home_msg)
    ImageView homeMsg;
    @InjectView(R.id.home_msg_view)
    RelativeLayout homeMsgView;
    @InjectView(R.id.tv_select_school)
    TextView tvSelectSchool;
    @InjectView(R.id.msg_count)
    TextView msgCount;
    @InjectView(R.id.msg_alert)
    RelativeLayout msgAlert;
    @InjectView(R.id.titleView)
    RelativeLayout titleView;
    @InjectView(R.id.clickChangeSchool)
    TextView clickChangeSchool;
    @InjectView(R.id.eaccount_many)
    TextView eaccountMany;
    @InjectView(R.id.upqr_view)
    LinearLayout upqrView;
    @InjectView(R.id.iv_ecard_or_qr)
    ImageView ivEcardOrQr;
    @InjectView(R.id.tv_ecard_or_qr)
    TextView tvEcardOrQr;
    @InjectView(R.id.tv_ecard_balance)
    TextView tvEcardBalance;
    @InjectView(R.id.ll_ecard)
    LinearLayout llEcard;
    @InjectView(R.id.tv_fee_status)
    TextView tvFeeStatus;
    @InjectView(R.id.ll_fee)
    LinearLayout llFee;
    @InjectView(R.id.ll_buss)
    LinearLayout llBuss;
    @InjectView(R.id.img_bind_card)
    ImageView imgBindCard;
    @InjectView(R.id.tv_bind_card_title)
    TextView tvBindCardTitle;
    @InjectView(R.id.tv_bind_card_content)
    TextView tvBindCardContent;
    @InjectView(R.id.ll_bank_card)
    LinearLayout llBankCard;
    @InjectView(R.id.center_view)
    View centerView;
    @InjectView(R.id.img_bind)
    ImageView imgBind;
    @InjectView(R.id.tv_bind_title)
    TextView tvBindTitle;
    @InjectView(R.id.tv_bind_content)
    TextView tvBindContent;
    @InjectView(R.id.ll_ocp)
    LinearLayout llOcp;
    @InjectView(R.id.ll_home_gg)
    LinearLayout llHomeGg;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.scrollview)
    ScrollView scrollview;
    @InjectView(R.id.swipeView)
    SwipeRefreshLayout swipeView;

    private HomeContract.HomePresenter presenter;

    private String land_mark_image;
    private DisplayMetrics metrics;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        metrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    public static Fragment newInstance(String text) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadCustomerData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        new HomePresenter(this, getActivity());
        presenter.loadCustomerData();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void customerSuccess(Customer customer) {
        land_mark_image = customer.school.land_mark_image;
        presenter.loadBannerData();
    }

    @Override
    public void bannerSuccess(List<Advertise> list) {

        if (Util.isEmpty(land_mark_image)) {
            showAnim(null, list);
        } else {
            DisplayImageOptions config = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.loadImage(land_mark_image,config, new SimpleImageLoadingListener() {

                @Override
                public void onLoadingComplete(String imageUri, final View view,
                                              Bitmap loadedImage) {
//                    showAnim(loadedImage, list);
                }
            });
        }
    }

    private void showAnim(Bitmap loadedImage, List<Advertise> list) {
        if (loadedImage != null && loufang != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                loufang.setBackground(new BitmapDrawable(loadedImage));
            }
            int w = metrics.widthPixels * 13 / 14;
            int h = loadedImage.getHeight() * w / loadedImage.getWidth();
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(w, h);
            params.setMargins(0, 0, 0, -(int) (250 * metrics.density));
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            loufang.setLayoutParams(params);
        }

        ObjectAnimator.ofFloat(flBannerBg, "alpha", 0f, 1f).setDuration(1500).start();
        ObjectAnimator.ofFloat(sun, "alpha", 0f, 1f).setDuration(1500).start();

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator a1 = ObjectAnimator.ofFloat(treeView, "translationY", (int) metrics.density * 300, 0);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(treeView, "alpha", 0, 1);
        set.play(a1).with(a2);
        set.setDuration(600).start();

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(loufang, "translationY", 0, -(int) metrics.density * 250);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(loufang, "alpha", 0, 1).setDuration(1000);
        animatorSet.play(objectAnimator).with(objectAnimator1);
        animatorSet.setDuration(600);
        animatorSet.setStartDelay(300);
        animatorSet.start();

        ObjectAnimator objectAnimator_ani3 = ObjectAnimator.ofFloat(ani3, "translationY", 0, -(int) metrics.density * 250);
        objectAnimator_ani3.setDuration(600);
        objectAnimator_ani3.setStartDelay(500);
        objectAnimator_ani3.start();

        ObjectAnimator objectAnimator_ani4 = ObjectAnimator.ofFloat(ani4, "translationY", 0, -(int) metrics.density * 250);
        objectAnimator_ani4.setDuration(600);
        objectAnimator_ani4.setStartDelay(650);
        objectAnimator_ani4.start();

        ObjectAnimator objectAnimator_ani5 = ObjectAnimator.ofFloat(ani5, "translationY", 0, -(int) metrics.density * 250);
        objectAnimator_ani5.setDuration(600);
        objectAnimator_ani5.setStartDelay(650);
        objectAnimator_ani5.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (flBannerBg != null)
                    viewSaveToImage(flBannerBg);
            }
        }, 2000);

        if (list != null && list.size() > 0) {
            if (flBannerBg != null)
                showBanner(list);
        }
    }

    private void showBanner(final List<Advertise> list) {
        ObjectAnimator objectAni = ObjectAnimator.ofFloat(flBannerBg, "alpha", 1, 0).setDuration(1500);
        objectAni.setDuration(1000);
        objectAni.setStartDelay(3000);
        objectAni.start();
        objectAni.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                flBannerBg.setVisibility(View.GONE);
                bannerView.setVisibility(View.VISIBLE);
                //加入banner
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                BannerFragment bannerFragment = new BannerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BannerFragment.EXTRA_DATA, (Serializable) list);
                bannerFragment.setArguments(bundle);
                transaction.replace(R.id.banner_view, bannerFragment);
                transaction.commitAllowingStateLoss();
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }

    public void viewSaveToImage(View view) {
        try {
            view.setDrawingCacheEnabled(true);
            view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            view.setDrawingCacheBackgroundColor(Color.WHITE);
            // 把一个View转换成图片
            Bitmap cachebmp = loadBitmapFromView(view);
            // 添加水印
            Bitmap bitmap = Bitmap.createBitmap(createWatermarkBitmap(cachebmp));
            FileOutputStream fos;
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                File sdRoot = Environment.getExternalStorageDirectory();
                if (!Util.isEmpty(App.mAxLoginSp.getLadmarkName())) {
                    File file = new File(sdRoot, App.mAxLoginSp.getLadmarkName());
                    file.delete();
                }
                String ladMrkLocalName = (int) (Math.random() * 100 + 1) + ".PNG";
                File file = new File(sdRoot, ladMrkLocalName);
                App.mAxLoginSp.setLadmarkName(ladMrkLocalName);
                fos = new FileOutputStream(file);
            } else
                throw new Exception("创建文件失败!");
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.destroyDrawingCache();
    }

    // 为图片target添加水印
    private Bitmap createWatermarkBitmap(Bitmap target) {
        Bitmap bmp = null;
        try {
            int w = target.getWidth();
            int h = target.getHeight();
            bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            Paint p = new Paint();
            p.setAntiAlias(true);// 去锯齿
            canvas.drawBitmap(target, 0, 0, p);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    private Bitmap loadBitmapFromView(View v) {
        Bitmap bmp = null;
        try {
            int w = v.getWidth();
            int h = v.getHeight();
            bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            c.drawColor(Color.WHITE);
            /** 如果不设置canvas画布为白色，则生成透明 */
            v.layout(0, 0, w, h);
            v.draw(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    public void getEcardDataSuccess(HomePresenter.EcardResponse ecardResponse) {

    }

    @Override
    public void setPresenter(HomeContract.HomePresenter presenter) {
            this.presenter=presenter;
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadError(String errorMsg) {

    }

    @Override
    public void loadComplete() {

    }
}
