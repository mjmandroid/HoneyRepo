package com.beautystudiocn.allsale.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.beautystudiocn.allsale.R;
import com.beautystudiocn.allsale.util.bean.Contact;
import com.beautystudiocn.allsale.util.bean.Contacts;
import com.beautystudiocn.allsale.util.bean.Contact;
import com.beautystudiocn.allsale.util.bean.Contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <br> ClassName:   ContactUtil
 * <br> Description: 手机联系人工具类
 * <br>
 * <br> Author:      wujianghua
 * <br> Date:       2015/9/29 10:46
 */
public class ContactUtil {
    private static Map<String, String> mContacts;
    private static boolean isPrepared = false;

    /**
     * <br> Description: 获取手机联系人数据
     * <br> Author:      wujianghua
     * <br> Date:         2015/9/29 10:46
     *
     * @param context 上下文
     * @return 返回联系人集合数据
     */
    public static synchronized Map<String, String> getmContacts(final Context context) {
        if (mContacts == null || !isPrepared) {
            if (mContacts == null) {
                mContacts = new TreeMap<String, String>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mContacts.putAll(getmContactsUtil(context));
                        isPrepared = true;
                    }
                }).start();
                return null;
            }
            if (!isPrepared) {
                return null;
            }
        }
        return mContacts;
    }

    private static synchronized Map<String, String> getmContactsUtil(Context context) {
        Map<String, String> mContacts_temp = new TreeMap<String, String>();
        try {
            String[] mContactsProjection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                    // ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    /* ContactsContract.Contacts.PHOTO_ID */};
            ContentResolver resolver = context.getContentResolver();
            Cursor phoneCursor = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    mContactsProjection, null, null, null);
            while (phoneCursor != null && phoneCursor.moveToNext()) {
                Long CONTACT_ID = phoneCursor.getLong(0);
                String NUMBER = phoneCursor.getString(1);
                String DISPLAY_NAME = phoneCursor.getString(2);
                mContacts_temp.put(NUMBER == null ? "" : NUMBER, DISPLAY_NAME == null ? "" : DISPLAY_NAME);
            }
        } catch (Exception e) {
        }
        return mContacts_temp;
    }

    /**
     * <br> Description: 根据联系人ID查询联系人
     * <br> Author:      wujianghua
     * <br> Date:         2015/9/29 10:46
     *
     * @param context 上下文
     * @param cursor  联系人cursor
     * @return 返回联系人Contact对象
     */
    public static Contact getContactPhone(Context context, Cursor cursor) {
        // TODO Auto-generated method stub
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            String personName = "";
            ArrayList<String> mPhoneNums = new ArrayList<String>();
            if (phone != null && phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index_phone = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int index_name = phone
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    personName = phone.getString(index_name);
                    mPhoneNums.add(phone.getString(index_phone));
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
                Contact mContact = new Contact();
                mContact.setName(personName);
                mContact.setPhoneNums(mPhoneNums);
                return mContact;
            }
        }
        return null;
    }

    /**
     * <br> Description: 选择一个号码
     * <br> Author:      wujianghua
     * <br> Date:         2015/9/29 10:46
     *
     * @param activity        页面activity对象
     * @param context         上下文
     * @param mPhoneNums      要筛选的号码集合
     * @param mCPhonelistener 成功选中号码的回调接口类
     */
    public static void ChangePhoneNum(Activity activity, Context context, final ArrayList<String> mPhoneNums, final ChangePhoneNumListener mCPhonelistener) {
        if (activity.isFinishing()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.lib_view_custom_spinner, null);
        ((TextView) view.findViewById(R.id.label)).setText("请选择号码");
        List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();
        for (String str : mPhoneNums) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("item_text", str);
            listdata.add(map);
        }
        SimpleAdapter mSAdapter = new SimpleAdapter(context, listdata,
                R.layout.lib_view_custom_spinner_item, new String[]{
                "item_text"}, new int[]{
                R.id.itemText});
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setContentView(view);
        ListView listview = (ListView) view.findViewById(R.id.custom_spinner_list);
        listview.setDivider(new ColorDrawable(view.getContext().getResources().getColor(R.color.lib_dividing_line_light)));
        listview.setDividerHeight(UIUtil.dp2px(0.5f));
        listview.setAdapter(mSAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCPhonelistener != null) {
                    // 选中的号码
                    mCPhonelistener.onChangePhone(mPhoneNums.get(i));
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * <br> Description: 获取联系人信息
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/7/25 11:33
     *
     * @return 联系人信息集合
     */
    public static List<Contacts> getContacts(Context context) {
        List<Contacts> list = new ArrayList<Contacts>();
        list = getPhoneContacts(context,list);
        return list;
    }

    /**
     * <br> Description: 得到手机通讯录联系人信息
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/7/25 11:29
     *
     * @param list 通讯录联系人存储集合
     * @return 联系人信息集合
     */
    private static List<Contacts> getPhoneContacts(Context context, List<Contacts> list) {
        Cursor phoneCursor = null;
        Cursor phone = null;
        try {
            String[] PHONES_PROJECTION = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
            ContentResolver resolver = context.getContentResolver();
            // 获取手机联系人
            phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    //得到联系人名称
                    String contactName = phoneCursor.getString(0);
                    //得到联系人ID
                    Long contactId = phoneCursor.getLong(2);
                    phone = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    Set setPhone = new HashSet();
                    while (phone.moveToNext()) {
                        String userNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        setPhone.add(new String(userNumber));
                    }
                    String phoneNumber = "";
                    if (setPhone.size() > 0) {
                        phoneNumber = setPhone.toString();
                        phoneNumber = phoneNumber.substring(1, phoneNumber.length() - 1);
                    }
                    if (TextUtils.isEmpty(phoneNumber)) {
                        continue;
                    }
                    list.add(new Contacts(contactName, phoneNumber));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (phone != null) {
                phone.close();
            }
            if (phoneCursor != null) {
                phoneCursor.close();
            }
        }
        return list;
    }

    public interface ChangePhoneNumListener {
        void onChangePhone(String mChangephone);
    }

}
