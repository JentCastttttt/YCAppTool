package com.yc.android.presenter

import android.annotation.SuppressLint
import com.yc.toolutils.AppNetworkUtils
import com.yc.android.contract.AndroidCollectContract
import com.yc.android.model.helper.AndroidHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AndroidCollectPresenter : AndroidCollectContract.Presenter{

    private var mView : AndroidCollectContract.View

    constructor(view : AndroidCollectContract.View){
        this.mView = view
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    @SuppressLint("CheckResult")
    override fun getCollectArticleList(page: Int) {
        val instance = AndroidHelper.instance()
        instance.getCollectArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({bean->
                    mView.setDataView(bean)
                },{t ->
                    if(AppNetworkUtils.isConnected()){
                        mView.setDataErrorView(t.message)
                    }else{
                        mView.setNetWorkErrorView()
                    }
                })
    }

}