package cn.samblog.easymvp.sample.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.samblog.easymvp.sample.R;
import cn.samblog.easymvp.sample.presenter.other.IFirstFragmentPresenter;
import cn.samblog.easymvp.sample.presenter.other.ISecondFragmentPresenter;
import cn.samblog.easymvp.sample.ui.view.other.ISecondFragmentView;
import cn.samblog.lib.easymvp.annotation.Find;
import cn.samblog.lib.easymvp.annotation.OnClicked;
import cn.samblog.lib.easymvp.annotation.Resource;
import cn.samblog.lib.easymvp.ui.fragment.BaseChildFragment;

/**
 * fragment 的 fragment
 */
@Resource(layoutResource = R.layout.fragment_secondfragment)
public class SecondFragment extends BaseChildFragment<FirstFragment> implements ISecondFragmentView {

    @Find(R.id.tv_message)
    TextView tvMessage;

    @OnClicked(R.id.btn_answer)
    void onAnswerCliked(View view)
    {
        ISecondFragmentPresenter presenter = getBaseFragment().getBaseActivity().secondFragmentPresenter;
        presenter.loadMessage();
    }

    @Override
    protected void initViews(Bundle savedInstanceState, View parentView) {


    }

    @Override
    public void setSecondMessage(String message) {
        tvMessage.setText(message);
    }
}
