package com.yaratech.yaratube.ui.product_detail;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.ui.comment.CommentDialog;
import com.yaratech.yaratube.ui.login.LoginDialog;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailContract.View,
        View.OnClickListener{

    ProgressBar progressBar;
    Button sendComment;
    ImageView productAvatar;
    TextView productTitle, productAbout;
    ProductDetailContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        presenter = new ProductDetailPresenter(getApplicationContext(), this);

        progressBar = findViewById(R.id.progressbar);
        sendComment = findViewById(R.id.send_comment);
        progressBar.setVisibility(View.GONE);
        productAvatar = findViewById(R.id.product_avatar);
        productTitle = findViewById(R.id.product_title);
        productAbout = findViewById(R.id.product_about);
        Intent intent = getIntent();
        presenter.fetchProductDetailFromRemote(intent.getIntExtra("PRODUCT_ID", 0));

    }

    @Override
    public void showProductDetail(ProductDetail productDetail) {
        Log.e("zara",productDetail.getDescription());
        Log.e("zara","mara");
        Log.e("zara",productDetail.getAvatar().getXxxdpi()+"");
        Glide.with(getApplicationContext()).load(productDetail.getAvatar().getXxxdpi()).into(productAvatar);
        productTitle.setText(productDetail.getName());
        productAbout.setText(productDetail.getDescription());
        sendComment.setOnClickListener(this);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {
        CommentDialog commentDialog = new CommentDialog();
        FragmentManager fragmentManager = getSupportFragmentManager();
        commentDialog.show(fragmentManager, "comment dialog");
    }
}