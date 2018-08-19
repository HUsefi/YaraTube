package com.yaratech.yaratube.ui.productlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductList;
import com.yaratech.yaratube.ui.MenuActivity;
import com.yaratech.yaratube.ui.OnProductItemClick;

import java.util.List;

public class ProductListFragment extends Fragment implements ProductListContract.View,
        ProductListRecyclerViewAdapter.OnItemClickListener {

    ProductListPresenter productListPresenter;
    OnProductItemClick onProductItemClick;
    private ProgressBar progressBar;
    public static final String PRODUCT_LIST_FRAGMENT_TAG = "ProductList";
    private RecyclerView recyclerView;
    private ProductListRecyclerViewAdapter productListRecyclerViewAdapter;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("category_id", id);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof MenuActivity)
            onProductItemClick = (OnProductItemClick) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        onProductItemClick = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);
        productListPresenter = new ProductListPresenter(getContext(), this);
        recyclerView = view.findViewById(R.id.product_list_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                LinearLayoutManager.VERTICAL, false));
        productListRecyclerViewAdapter = new ProductListRecyclerViewAdapter(getContext()
                , this);
        recyclerView.setAdapter(productListRecyclerViewAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int id = getArguments().getInt("category_id");
        productListPresenter.fetchProductListFromRemote(id);
    }

    @Override
    public void showListProducts(List<Product> productList) {
        productListRecyclerViewAdapter.setData(productList);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void getProductItem(Product product) {
        onProductItemClick.onClick(product);
    }
}