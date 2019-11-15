package com.example.fridge

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MaterialItemTouchHelper(adapter: MaterialAdapter, context: Context, dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs)
{
    private val dragAdapter = adapter
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        //var dragFlags : Int = ItemTouchHelper.ANIMATION_TYPE_DRAG
        val dragFlags : Int = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN).or(ItemTouchHelper.LEFT).or(ItemTouchHelper.RIGHT)//.or(ItemTouchHelper.ANIMATION_TYPE_DRAG)
//        var swipeFlags : Int = ItemTouchHelper.DOWN
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, 0)
        //swipe 액션이 되지 않게 하기 위해 0을 넘김 원래는 swipeFlags
    }

    override fun onMove(recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        dragAdapter.swapItems(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //안씀
    }

    override fun getSwipeDirs(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return 0 //return 0 하면 드래그만 가능하고 스와이프는 불가능
        //return super.getSwipeDirs(recyclerView, viewHolder)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }
}