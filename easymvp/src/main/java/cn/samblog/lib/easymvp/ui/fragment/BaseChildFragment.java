package cn.samblog.lib.easymvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhy.autolayout.utils.AutoUtils;
import cn.samblog.lib.easymvp.utils.EasyHelper;

/**
 * @author Sam
 */
public abstract class BaseChildFragment<T extends BaseFragment>  extends Fragment {

    protected View mParentView;
    private SparseArray<View> views =new SparseArray<>();

    protected void setLayoutBefore() {
    }


    protected abstract void initViews(Bundle savedInstanceState, View parentView);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setLayoutBefore();
        super.onCreate(savedInstanceState);
    }

    @Deprecated
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mParentView) {
            mParentView = inflater.inflate( EasyHelper.getResource(this),container , false);
            AutoUtils.auto(mParentView);
        }
        return mParentView;
    }

    @Deprecated
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null  != mParentView) {

            EasyHelper.inject(this,mParentView, getContext(), null);
            initViews(savedInstanceState, mParentView);
        }
    }

    protected void toastWithSnackbar(String message)
    {
        toastWithSnackbar(message, "好的，我知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void toastWithSnackbar(String message, String action, View.OnClickListener onClickListener)
    {
        Snackbar.make(mParentView, message, Snackbar.LENGTH_SHORT).setAction(action, onClickListener).show();
    }


    protected void toast(String message)
    {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    protected View getParentView() {
        return mParentView;
    }

    protected  final  T getBaseFragment()
    {
        return (T) getParentFragment();
    }


}
