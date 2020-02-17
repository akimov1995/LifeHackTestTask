package com.example.lifehacktesttask.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.lifehacktesttask.R
import com.example.lifehacktesttask.mvp.model.Company
import com.example.lifehacktesttask.mvp.presenter.InfoPresenter
import com.example.lifehacktesttask.mvp.view.InfoView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso


class InfoActivity : MvpAppCompatActivity(), InfoView, OnMapReadyCallback {

    @InjectPresenter
    lateinit var infoPresenter: InfoPresenter
    private lateinit var descriptionTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var siteTextView: TextView
    private lateinit var placeNotFoundTextView: TextView
    private lateinit var map: GoogleMap
    private lateinit var imageView: ImageView

    @ProvidePresenter
    fun provideInfoPresenter(): InfoPresenter? {
        return InfoPresenter(intent.extras.getString("id").toInt())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_info)

        descriptionTextView = findViewById(R.id.description)
        imageView = findViewById(R.id.imageView)
        nameTextView = findViewById(R.id.name)
        phoneTextView = findViewById(R.id.phoneNumber)
        siteTextView = findViewById(R.id.site)
        placeNotFoundTextView = findViewById(R.id.place_not_found)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun showCompanyInfo(company: Company) {
        nameTextView.text = company.name
        descriptionTextView.text = company.description
        val baseUrl = getString(R.string.base_url)
        val imageUrl = baseUrl + company.img

        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.image_not_found)
            .fit()
            .into(imageView)

        if (company.lat != 0.0 && company.lon != 0.0) {
            val place = LatLng(company.lat, company.lon)
            val zoom = 17
            map.addMarker(MarkerOptions().position(place).title(company.name))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoom.toFloat()))
        } else {
            placeNotFoundTextView.visibility = View.VISIBLE
            placeNotFoundTextView.text = getString(R.string.place_not_found_message)
        }

        if (!company.phone.isEmpty()) {
            phoneTextView.text = getString(R.string.phone) + " " + company.phone
        } else {
            phoneTextView.text =
                getString(R.string.phone) + " " + getString(R.string.contact_not_found_message)
        }
        if (!company.www.isEmpty()) {
            siteTextView.text = getString(R.string.site) + " " + company.www
        } else {
            siteTextView.text =
                getString(R.string.site) + " " + getString(R.string.contact_not_found_message)

        }
    }

    override fun showError(text: String) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0!!
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
    }
}
