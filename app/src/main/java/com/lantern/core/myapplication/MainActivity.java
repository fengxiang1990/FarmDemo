package com.lantern.core.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.ActivityManager;
import android.app.Service;
//import android.arch.lifecycle.Lifecycle;
//import android.arch.lifecycle.LifecycleOwner;
//import android.arch.lifecycle.LifecycleRegistry;
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelStore;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bun.miitmdid.core.IIdentifierListener;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.supplier.IdSupplier;
import com.lantern.core.myapplication.view.TreeImageView;
import com.uodis.opendevice.aidl.OpenDeviceIdentifierService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IIdentifierListener {


    String tag = "MainActivity";
//    Test test = new Test();

    List<Disposable> disposables;

    TreeImageView imgTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("fxa", this.toString() + " onCreate");
        setContentView(R.layout.activity_main);
        disposables = new ArrayList<>();
//
//        test.init();
//        Test.context = this;

        findViewById(R.id.test).setOnClickListener(this);
        imgTree = (TreeImageView) findViewById(R.id.img_tree);
        water();
        jiaoshui();
        getOAID();
        treeClick();
//       Disposable disposable1 = Observable.just(1)
//                .map(new Function<Integer, String>() {
//
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        Log.e(tag,Thread.currentThread().getName());
//                        return "fxa->"+integer;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String integer) throws Exception {
//                      Log.e(tag,integer+"");
//                    }
//                });
//
//        Disposable disposable2 =Observable.just(2)
//                .map(new Function<Integer, String>() {
//
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        Log.e(tag,Thread.currentThread().getName());
//                        return "fxa->"+integer;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String integer) throws Exception {
//                        Log.e(tag,integer+"");
//                    }
//                });
//
//        Disposable disposable3 = Observable.just(3)
//                .map(new Function<Integer, String>() {
//
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        Log.e(tag,Thread.currentThread().getName());
//                        return "fxa->"+integer;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String integer) throws Exception {
//                        Log.e(tag,integer+"");
//                    }
//                });

        Observable<Integer> observable1 = Observable.just(1)
                .map(new Function<Integer, Integer>() {

                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        Log.e(tag, Thread.currentThread().getName());
                        return integer * 10;
                    }
                });
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());

        Observable<String> observable2 = Observable.just(2)
                .map(new Function<Integer, String>() {

                    @Override
                    public String apply(Integer integer) throws Exception {
                        Log.e(tag, Thread.currentThread().getName());
                        return "fxa->" + integer;
                    }
                });
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());

        Observable<String> observable3 = Observable.just(3)
                .map(new Function<Integer, String>() {

                    @Override
                    public String apply(Integer integer) throws Exception {
                        Log.e(tag, Thread.currentThread().getName());
                        return "fxa->" + integer;
                    }
                });
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());

//        observable1.zipWith(observable2, new BiFunction<String,String,String>() {
//            @Override
//            public String apply(String o, String o2) throws Exception {
//                return o+o2;
//            }
//        }).zipWith(observable3, new BiFunction<String,String,String>() {
//            @Override
//            public String apply(String o, String o2) throws Exception {
//                return o+o2;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String o) throws Exception {
//               Log.e(tag,o);
//            }
//        });


        Observable.combineLatest(observable1, observable2, observable3, new Function3<Integer, String, String, Object>() {
            @Override
            public Object[] apply(Integer integer, String s, String s2) throws Exception {
                return new Object[]{integer, s, s2};
            }
        }).flatMap(new Function<Object, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(Object o) throws Exception {
                return Observable.fromArray(o);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(tag,o.getClass()+"");
//                        Log.e(tag, o.length + "");
//                      Observable.fromArray(o)
//                              .subscribe(new Consumer<Object>() {
//                                  @Override
//                                  public void accept(Object o) throws Exception {
//                                      Log.e(tag,o+"");
//                                  }
//                              });
                    }
                }).isDisposed();

//        disposables.add(disposable1);
//        disposables.add(disposable2);
//        disposables.add(disposable3);

    }

    private void treeClick(){
        imgTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgTree, "scaleX", 1f, 1f);
                ObjectAnimator scaleY = ObjectAnimator.ofInt(imgTree, "paddingTop",100,50,100);
                final AnimatorSet animatorSet = new AnimatorSet();  //组合动画
                animatorSet.setInterpolator(new DecelerateInterpolator());
                animatorSet.playTogether(scaleX,scaleY); //设置动画
                animatorSet.setDuration(500);  //设置动画时间
                animatorSet.start();
            }
        });

    }
    private void getOAID() {
        int code =  MdidSdkHelper.InitSdk(this,true,this);
        Log.e(tag,"code->"+code);

        Intent bindIntent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        bindIntent.setPackage("com.huawei.hwid");
        bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            OpenDeviceIdentifierService oaidService = OpenDeviceIdentifierService.Stub.asInterface(service);
            try {
                String oaid = oaidService.getOaid();
                boolean isTrackLimited = oaidService.isOaidTrackLimited();
                Log.e(tag,"huawei->"+oaid);
                Log.e(tag,"huawei isTrackLimited->"+isTrackLimited);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    public void test() {
        Handler handler;
        Looper looper;

    }

    public void reset(){
        ImageView imgJiaoshui = (ImageView) findViewById(R.id.img_jiaoshui);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(imgJiaoshui,"translationX",0);
        ObjectAnimator translationY =  ObjectAnimator.ofFloat(imgJiaoshui,"translationY",0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgJiaoshui, "scaleX", 2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgJiaoshui, "scaleY", 2f, 1f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(imgJiaoshui, "rotation", 0);

        final AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(rotate,scaleX,scaleY,translationX,translationY); //设置动画
        animatorSet.setDuration(500);  //设置动画时间
        animatorSet.start();
    }

    public void jiaoshui(){
        final ImageView imgJiaoshui = (ImageView) findViewById(R.id.img_jiaoshui);
        final ImageView imgRain = (ImageView) findViewById(R.id.img_rain);
        final Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        Log.e(tag,"x->"+point.x);
        Log.e(tag,"y->"+point.y);
        float x = -point.x / 2f / 10f;
        float x1 = 1* x;
        float x2 = 1.5f * x;
        float x3 = 2.0f * x;

        float y2 = -point.y / 3f;
        ObjectAnimator translationX = ObjectAnimator.ofFloat(imgJiaoshui,"translationX",0,x1,x1+x2
        ,x1+x2+x3);
        ObjectAnimator translationY =  ObjectAnimator.ofFloat(imgJiaoshui,"translationY",0,y2);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgJiaoshui, "scaleX", 1f, 2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgJiaoshui, "scaleY", 1f, 2f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(imgJiaoshui, "rotation", -2,-4,-6,-20,-45);

        final AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(rotate,scaleX,scaleY,translationX,translationY); //设置动画
        animatorSet.setDuration(500);  //设置动画时间

        imgJiaoshui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet.start();
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Log.e(tag,"imgJiaoshui x->"+imgJiaoshui.getBottom()+" "+imgJiaoshui.getLeft());

                ObjectAnimator translationX = ObjectAnimator.ofFloat(imgRain,"translationX",0,-20);
                ObjectAnimator translationY =  ObjectAnimator.ofFloat(imgRain,"translationY",0,200);
                AnimatorSet animatorSet1 = new AnimatorSet();  //组合动画
                animatorSet1.setInterpolator(new DecelerateInterpolator());
                animatorSet1.playTogether(translationX,translationY); //设置动画
                animatorSet1.setDuration(500);  //设置动画
                animatorSet1.start();
                imgRain.setVisibility(View.VISIBLE);
                animatorSet1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imgRain.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imgRain.setVisibility(View.GONE);
                                reset();
                            }
                        },500);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public void water(){
        ImageView imgWater = (ImageView) findViewById(R.id.img_water);


        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgWater, "scaleX", 0, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgWater, "scaleY", 0, 1f);

        animatorSetsuofang.setDuration(1000);
        animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.start();


        ObjectAnimator translationYUp =  ObjectAnimator.ofFloat(imgWater,"translationY",0,50f);
        ObjectAnimator translationYDown = ObjectAnimator.ofFloat(imgWater,"translationY",0,-50f);


        final AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.setInterpolator(new CycleInterpolator(3));
        animatorSet.playTogether(translationYUp,translationYDown); //设置动画
        animatorSet.setDuration(10000);  //设置动画时间

        animatorSetsuofang.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSet.start(); //启
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.test) {
//              test.test2();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "xxx", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();

                }
            });

//              handler.postDelayed(new Runnable() {
//                  @Override
//                  public void run() {
//                      Log.e("fxa","xxxx");
//                  }
//              },30000);
        }
    }

    Handler handler = new Handler();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        Log.e("fxa", this.toString() + " onDestroy");
        for (Disposable disposable : disposables) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposables.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnSupport(boolean b, IdSupplier idSupplier) {
        Log.e(tag,"OnSupport->"+b);
        if(b){
            Log.e(tag,"OnSupport oaid->"   +idSupplier.getOAID()+"  aaid->"+idSupplier.getAAID()+" vaid->"+idSupplier.getVAID());
        }
    }

//    static class Test implements LifecycleOwner {
//
//        String tag = "Test";
//
//        LifecycleRegistry lifecycleRegistry;
//        MyViewModel myViewModel;
//        ViewModelStore store;
//
//
//        void test2() {
//            myViewModel.test();
//        }
//
//        static Context context;
//
//        private Test() {
//            myViewModel = new MyViewModel();
//            lifecycleRegistry = new LifecycleRegistry(this);
//            store = new ViewModelStore();
//            lifecycleRegistry.markState(Lifecycle.State.STARTED);
//            myViewModel.test();
////            new Thread(){
////                @Override
////                public void run() {
////                    try {
////                        sleep(30000);
////                        Log.e("xxx","sleep 30000");
////                    }catch (Exception e){
////                        e.printStackTrace();
////                    }
////                }
////            }.start();
//        }
//
//        void init() {
//            myViewModel.liveData.observe(this, new Observer<String>() {
//                @Override
//                public void onChanged(@Nullable String s) {
//                    Log.e(tag, s);
//                }
//            });
//        }
//
//        @NonNull
//        @Override
//        public Lifecycle getLifecycle() {
//            return lifecycleRegistry;
//        }
//    }

}
