package com.yaratech.yaratube.data.sourse.product_detail;

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
import com.yaratech.yaratube.data.model.ProductList;

import java.util.List;

public class ProductDetailFragment extends Fragment implements ProductDetailContract.View,
        ProductDetailRecyclerViewAdapter.OnItemClickListener {

    ProductDetailPresenter productListPresenter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ProductDetailRecyclerViewAdapter productListRecyclerViewAdapter;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("category_id", id);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
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
        productListPresenter = new ProductDetailPresenter(getContext(), this);
        recyclerView = view.findViewById(R.id.product_list_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                LinearLayoutManager.VERTICAL, false));
        productListRecyclerViewAdapter = new ProductDetailRecyclerViewAdapter(getContext()
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
    public void showListProducts(List<ProductList> productLists) {
        productListRecyclerViewAdapter.setData(productLists);
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
    public void getProductItem(ProductList product) {
        ((ProductDetailFragment.OnProductClickListener)getContext()).onItemClicked(product);
    }

    public interface OnProductClickListener{
        void onItemClicked(ProductList product);
    }
}