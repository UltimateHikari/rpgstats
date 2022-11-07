package com.nsu.rpgstats.ui.items;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityItemsBinding;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.ui.NpcActivity;
import com.nsu.rpgstats.ui.tags.TagsActivity;
import com.nsu.rpgstats.ui.parameters.ParametersActivity;
import com.nsu.rpgstats.ui.properties.PropertiesActivity;
import com.nsu.rpgstats.viewmodel.ItemViewModel;

import java.util.List;

public class ItemsActivity extends AppCompatActivity implements ItemsAdapter.OnItemClickListener {
    private ActivityItemsBinding binding;

    private List<Item> mItemList;
    private ItemsAdapter mItemsAdapter;
    private Integer gameSystemId;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("ItemActivity", "onCreate");
        super.onCreate(savedInstanceState);
        binding = ActivityItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gameSystemId = ((RpgstatsApplication)getApplication()).appContainer.currentGameSystem.getId();
        if (savedInstanceState == null) {
            itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        }
        mItemList = itemViewModel.getItems(gameSystemId).getValue();
        itemViewModel.getItems(gameSystemId).observe(this, items -> {
            Log.e("observer", "set list " + items.toString());
            mItemsAdapter.setItemList(items);
        });

        RecyclerView recyclerView = binding.ItemList.recyclerVIew;
        mItemsAdapter = new ItemsAdapter(mItemList, this, this);
        recyclerView.setAdapter(mItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setListeners();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ItemActivity", "onStart");
        itemViewModel.loadItems(gameSystemId);
    }

    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }

    private void setListeners() {
        setOnClickCreateActivity(binding.BottomBar.tagsButton, TagsActivity.class);
        setOnClickCreateActivity(binding.BottomBar.npcButton, NpcActivity.class);
        setOnClickCreateActivity(binding.BottomBar.parametersButton, ParametersActivity.class);
        setOnClickCreateActivity(binding.BottomBar.propertiesButton, PropertiesActivity.class);
        binding.ItemActivityAddItemButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddItemActivity.class);
            intent.putExtra("game_system_id", gameSystemId.toString());
            startActivity(intent);
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Item mItem = mItemList.get(position);
        Intent intent = new Intent(this, ViewItemInfoActivity.class);
        intent.putExtra("id", mItem.getId().toString());
        intent.putExtra("game_system_id", gameSystemId.toString());
        startActivity(intent);
    }
}
