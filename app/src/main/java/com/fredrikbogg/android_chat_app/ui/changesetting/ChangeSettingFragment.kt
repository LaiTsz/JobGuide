package com.fredrikbogg.android_chat_app.ui.changesetting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.App
import com.fredrikbogg.android_chat_app.data.EventObserver
import com.fredrikbogg.android_chat_app.data.Job
import com.fredrikbogg.android_chat_app.databinding.FragmentChangeSettingBinding
import com.fredrikbogg.android_chat_app.ui.home.JobAdaptor
import com.fredrikbogg.android_chat_app.util.convertFileToByteArray
import com.fredrikbogg.searchablemultiselectspinner.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_change_setting.*
import com.google.firebase.database.*
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ChangeSettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangeSettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val viewModel:ChangeSettingViewModel by viewModels {ChangeSettingViewModelFactory(App.myUserID)  }
    private lateinit var viewDataBinding: FragmentChangeSettingBinding
    private lateinit var dbRef: DatabaseReference
    private val selectImageIntentRequestCode = 1
    private var majorItems: MutableList<SearchableItem> = ArrayList()
    private var careerItems: MutableList<SearchableItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentChangeSettingBinding.inflate(inflater, container, false)
            .apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //for (i in 0..20) {
            //items.add(SearchableItem("Item $i", "$i"))
      //  }
        getData(majorItems,"Major")
        getData(careerItems,"Career")

        buttonCareer.setOnClickListener {
            SearchableMultiSelectSpinner.show(view.context, "Select Career Direction(s)","Done", careerItems, object :
                SelectionCompleteListener {

                override fun onCompleteSelection(selectedItems: ArrayList<SearchableItem>) {
                    val careerList = ArrayList<String>()
                    for (i in selectedItems)
                    {
                        careerList.add(i.text.toString())
                    }
                    viewModel.changeUserCareer(careerList)

                }

            })
        }
        buttonMajor.setOnClickListener {
            SearchableSingleSelectSpinner.show(view.context, "Select Major",majorItems, object :
                SingleSelectionCompleteListener {
                override fun onCompleteSelection(selectedItem: SearchableItem) {
                    viewModel.changeUserMajor(selectedItem.text.toString())

                }

            })
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == selectImageIntentRequestCode) {
            data?.data?.let { uri ->
                convertFileToByteArray(requireContext(), uri).let {
                    viewModel.changeUserImage(it)
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.editStatusEvent.observe(viewLifecycleOwner,
            EventObserver { showEditStatusDialog() })

        viewModel.editImageEvent.observe(viewLifecycleOwner,
            EventObserver { startSelectImageIntent() })


    }

    private fun showEditStatusDialog() {
        val input = EditText(requireActivity() as Context)
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Bio (<= 40 characters)")
            setView(input)
            setPositiveButton("Ok") { _, _ ->
                val textInput = input.text.toString()
                if (!textInput.isBlank() && textInput.length <= 40) {
                    viewModel.changeUserStatus(textInput)
                }
            }
            setNegativeButton("Cancel") { _, _ -> }
            show()
        }
    }

    private fun startSelectImageIntent() {
        val selectImageIntent = Intent(Intent.ACTION_GET_CONTENT)
        selectImageIntent.type = "image/*"
        startActivityForResult(selectImageIntent, selectImageIntentRequestCode)
    }

    private fun getData(items:MutableList<SearchableItem>,path:String){
        dbRef = FirebaseDatabase.getInstance().getReference(path)
        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(i in snapshot.children){
                        var text= i.child("text").value.toString()
                        var code =i.child("code").value.toString()
                        items.add(SearchableItem(text , code ))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }



}