package scau.duolian.oa.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import scau.duolian.oa.R;
import scau.duolian.oa.adapter.MessageAdapter;
import scau.duolian.oa.base.BaseUiAuth;
import scau.duolian.oa.model.Message;
import scau.duolian.oa.util.StringUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class UiMessageCenter extends BaseUiAuth {
	private static List<Message> messages = new ArrayList<Message>();
	private static List<Message> selMessages = new ArrayList<Message>();
	private static String keyword = null;
	/**
	 * 搜索是否开启
	 */
	private static boolean filter = false;
	private ListView lv_messsages = null;
	private EditText edt_search = null;
	
	private static MessageAdapter adapter = null;
	
	public final static int REFRESH_CODE = 1; 
	public static Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case REFRESH_CODE:
					refresh();
					break;
				default:
					break;
			}
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_message_center);
		db = FinalDb.create(this);
		initCon();
		initData();
	}
	
	static FinalDb db = null;
	
	private void initData() {
		
		refresh();
	}
	
	private void initCon() {
		// TODO Auto-generated method stub
		lv_messsages = (ListView) findViewById(R.id.lv_messsages);
		edt_search = (EditText) findViewById(R.id.edt_search);
		super.menu = findViewById(R.id.menu);
		adapter = new MessageAdapter(this, messages,db);
		lv_messsages.setAdapter(adapter);
	}

	private static void refresh(){
		messages = db.findAll(Message.class, "dt");
		adapter.messages = messages;
		adapter.notifyDataSetChanged();
	}
	
	///btn
	public void doSearch(View view) {
		// TODO Auto-generated method stub
		keyword = edt_search.getText().toString();
		selMessages.clear();
		if(!StringUtil.isBlank(keyword)){
			for(int i =0 ;i<messages.size();i++){
				Message m = messages.get(i);
				if(m.find(keyword))
					selMessages.add(m);
			}
			adapter.messages  = selMessages;
		}else{
			adapter.messages = messages;
		}
		adapter.notifyDataSetChanged();
	}
	
}
