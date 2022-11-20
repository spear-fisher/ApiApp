package jp.techacademy.keita.michikawa.apiapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jp.techacademy.keita.michikawa.apiapp.databinding.FragmentFavoriteBinding

class FavoriteFragment: Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteAdapter by lazy { FavoriteAdapter(requireContext()) }

    // FavoriteFragment -> MainActivity に削除を通知する
    private var fragmentCallback : FragmentCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCallback) {
            fragmentCallback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root // fragment_favorite.xmlが反映されたViewを作成して、returnします
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ここから初期化処理を行う
        // FavoriteAdapterのお気に入り削除用のメソッドの追加を行う
        favoriteAdapter.apply {
            // Adapterの処理をそのままActivityに通知
            onClickDeleteFavorite = {
                fragmentCallback?.onDeleteFavorite(it.id)
            }
            // Itemをクリックしたとき
            onClickItem = {
                fragmentCallback?.onClickItem(it)
            }
        }
        // RecyclerViewの初期化
        binding.recyclerView.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext()) // 一列ずつ表示
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
        updateData()
    }

    fun updateData() {
        favoriteAdapter.refresh(FavoriteShop.findAll())
        binding.swipeRefreshLayout.isRefreshing = false
    }
}