package com.krishapps.mynotes;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;


public class DeleteQuoteFragment extends DialogFragment {
	
	public interface VerificationDialogListener{
		public void deleteButtonPressed();
		public void cancelButtonPressed();
	}
	
	VerificationDialogListener callBackListener;
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			callBackListener = (VerificationDialogListener) activity;
			
		}catch(ClassCastException e){
			throw new ClassCastException(activity.toString());
		}
	}
	public Dialog onCreateDialog(Bundle savedInstanceState){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setMessage(R.string.delete_quote_verification);
		builder.setPositiveButton(R.string.context_menu_delete, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				callBackListener.deleteButtonPressed();
			}
		});
		
		builder.setNegativeButton(R.string.menu_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				callBackListener.cancelButtonPressed();
			}
		});
		
		return builder.create();
	}
}
