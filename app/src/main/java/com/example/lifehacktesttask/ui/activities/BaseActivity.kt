package com.example.testapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.lifehacktesttask.CompanyAdapter
import com.example.lifehacktesttask.R
import com.example.lifehacktesttask.mvp.model.Company
import com.example.lifehacktesttask.mvp.presenter.BasePresenter
import com.example.lifehacktesttask.mvp.view.BaseView


class BaseActivity : MvpAppCompatActivity(), BaseView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CompanyAdapter

    @InjectPresenter
    lateinit var basePresenter: BasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        adapter = CompanyAdapter()
        val spanCount = 2
        recyclerView.layoutManager = GridLayoutManager(applicationContext, spanCount)
    }

    override fun showCompanyList(news: List<Company>) {
        adapter.setData(news)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun showError(text: String) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }
}