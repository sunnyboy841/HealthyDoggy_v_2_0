package com.example.healthydoggy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // 数据库信息
    private static final String DATABASE_NAME = "HealthyDoggyDB";
    //版本号：每次修改版本类型都要升这个数字
    private static final int DATABASE_VERSION = 3;

    // 用户表（COL：column 列，表示一个字段）
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "_id";
    public static final String COL_USER_NAME = "username";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PHONE = "phone";

    // 帖子表
    public static final String TABLE_POSTS = "posts";    //帖子
    public static final String COL_POST_ID = "_id";
    public static final String COL_POST_TITLE = "title";
    public static final String COL_POST_CONTENT = "content";   //内容
    public static final String COL_POST_AUTHOR = "author";  //作者
    public static final String COL_POST_TIME = "time";

    // 消息表
    public static final String TABLE_MESSAGES = "messages";
    public static final String COL_MSG_ID = "_id";
    public static final String COL_MSG_USER = "username";
    public static final String COL_MSG_CONTENT = "content";
    public static final String COL_MSG_TIME = "time";
    public static final String COL_MSG_POST_ID = "post_id";

    // 商品表
    public static final String TABLE_PRODUCTS = "products";  //产品
    public static final String COL_PRODUCT_ID = "_id";
    public static final String COL_PRODUCT_NAME = "name";
    public static final String COL_PRODUCT_PRICE = "price";
    public static final String COL_PRODUCT_DESC = "description";  //描述

    // 健康记录表
    public static final String TABLE_HEALTH = "health_records";   //健康记录
    public static final String COL_HEALTH_ID = "_id";
    public static final String COL_HEALTH_DATE = "date";
    public static final String COL_HEALTH_WEIGHT = "weight";
    public static final String COL_HEALTH_TEMP = "temperature";   //体温

    // 创建表语句

    //用户表
    //常见字段：
//    NULL	空值	字段为空
//    INTEGER	有符号整数（-2^63 到 2^63-1）	ID、年龄、数量
//    REAL	浮点数（8 字节，类似 double）	价格、分数、坐标
//    TEXT	文本字符串（UTF-8/16）	名字、描述、邮箱、手机号
//    BLOB	二进制大数据（如图片、文件）	头像、附件

//    约束	说明	示例
//1. PRIMARY KEY	主键：唯一标识一条记录，不能为空，自动创建索引	id INTEGER PRIMARY KEY
//2. AUTOINCREMENT	自动递增：配合 PRIMARY KEY 使用，ID 自动 +1	INTEGER PRIMARY KEY AUTOINCREMENT
//3. NOT NULL	非空：该字段不能为 NULL	username TEXT NOT NULL
//4. UNIQUE	唯一：该字段值在整个表中必须唯一	username TEXT UNIQUE
//5. DEFAULT	默认值：如果未提供值，使用默认值	status TEXT DEFAULT 'active'
//6. CHECK	检查：自定义条件验证数据	age INTEGER CHECK(age >= 0)
//7. FOREIGN KEY	外键：关联另一张表的主键（需开启外键支持）	user_id INTEGER REFERENCES users(id)
//8. COLLATE	排序规则：定义文本比较方式	name TEXT COLLATE NOCASE（忽略大小写）


    //用户表
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_USER_NAME + " TEXT UNIQUE NOT NULL, " +
            COL_USER_PASSWORD + " TEXT NOT NULL, " +    //not null:必须填，不可缺
            COL_USER_EMAIL + " TEXT, " +
            COL_USER_PHONE + " TEXT)";


    //帖子表
    private static final String CREATE_TABLE_POSTS = "CREATE TABLE " + TABLE_POSTS + " (" +
            COL_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_POST_TITLE + " TEXT NOT NULL, " +
            COL_POST_CONTENT + " TEXT NOT NULL, " +
            COL_POST_AUTHOR + " TEXT NOT NULL, " +
            COL_POST_TIME + " TEXT NOT NULL)";

    //消息表
    private static final String CREATE_TABLE_MESSAGES = "CREATE TABLE " + TABLE_MESSAGES + " (" +
            COL_MSG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_MSG_USER + " TEXT NOT NULL, " +
            COL_MSG_CONTENT + " TEXT NOT NULL, " +
            COL_MSG_TIME + " TEXT NOT NULL, " +
            COL_MSG_POST_ID + " INTEGER NOT NULL)";

    //产品表
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
            COL_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +   //integer整数
            COL_PRODUCT_NAME + " TEXT NOT NULL, " +
            COL_PRODUCT_PRICE + " REAL NOT NULL, " +  //real小数
            COL_PRODUCT_DESC + " TEXT NOT NULL)";

    //健康记录表
    private static final String CREATE_TABLE_HEALTH = "CREATE TABLE " + TABLE_HEALTH + " (" +
            COL_HEALTH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_HEALTH_DATE + " TEXT NOT NULL, " +
            COL_HEALTH_WEIGHT + " REAL NOT NULL, " +
            COL_HEALTH_TEMP + " REAL NOT NULL)";



    //super(...)：调用父类 SQLiteOpenHelper 的构造函数，传入四个参数
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //super：传给SQLiteOpenHelper（父类）
        //Context context：构造函数接收一个 Context 参数。Context 提供了访问系统资源和数据库文件路径所需的信息（如应用的包名、文件目录等）。常见的传入参数是 Activity 或 Application 对象。
        //DATABASE_NAME：数据库文件的名称（例如 "myapp.db"）。如果为 null，则数据库会以内存形式存在（不推荐常规使用）。
        //null：表示不使用自定义的 CursorFactory。大多数情况下传 null 即可。
        //DATABASE_VERSION：数据库版本号（整数），用于控制数据库升级和降级。初始值通常设为 1，后续修改表结构时需递增。
    }




    //特别说明：Context的用法
//    启动 Activity	context.startActivity(intent)
//🔹 启动 Service	context.startService(service)
//🔹 发送广播	context.sendBroadcast(intent)
//🔹 访问资源	context.getString(R.string.app_name)
//🔹 获取系统服务	LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//🔹 创建数据库/SharedPreferences	new DatabaseHelper(context)
//🔹 获取文件存储路径	context.getFilesDir()



//首次用app时候，创建数据库文件
    @Override
    public void onCreate(SQLiteDatabase db) {
//        使用 db.execSQL(建表方法函数名) 执行 SQL 语句来创建表。
//        每条 execSQL() 调用创建一张表，例如：
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_POSTS);
        db.execSQL(CREATE_TABLE_MESSAGES);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_HEALTH);
    }



    //数据库版本升级，重新创建所有表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        //"DROP TABLE IF EXISTS "如果存在，删表重建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH);
        onCreate(db);
    }


    // 新增：添加全局交流消息（用-1标识为非帖子评论的全局消息）
    public long addGlobalMessage(String username, String content, String time) {
        SQLiteDatabase db = getWritableDatabase();
        //  打开一个可以写入数据的数据库连接。如果数据库不存在，会自动创建；如果需要升级，也会触发 onUpgrade()。
        ContentValues values = new ContentValues();
        //ContentValues 是 Android 提供的键值对容器，专门用于数据库插入或更新（但只支持基本数据类型（String、Integer、Long、Byte[] 等。）

        //存储四个到相应字段上
        values.put(COL_MSG_USER, username);
        values.put(COL_MSG_CONTENT, content);
        values.put(COL_MSG_TIME, time);
        values.put(COL_MSG_POST_ID, -1); // 用-1标识全局消息（特殊值）

        long id = db.insert(TABLE_MESSAGES, null, values);
        //TABLE_MESSAGES	要插入的表名，如 "messages"
        // null	当 values 为空时，是否插入一个空行（设为 null 表示不允许）
        //  values	要插入的数据
        db.close();
        return id;
    }




    // 新增：获取所有全局交流消息
    //方法原型：
//    Cursor query(
//            String table,
//            String[] columns,
//            String selection,
//            String[] selectionArgs,
//            String groupBy,
//            String having,
//            String orderBy
//    )

    public Cursor getAllGlobalMessages() {
        SQLiteDatabase db = getReadableDatabase();   //获取全局交流信息

        //返回cursor对象
        return db.query(
                TABLE_MESSAGES,           // 表名
                null,                     // 要查询的列（null 表示所有列）
                COL_MSG_POST_ID + "=?",   // WHERE 查询条件
                new String[]{"-1"},       // 条件的参数值（替换 ?）
                null,                     // GROUP BY（分组）
                null,                     // HAVING （分组后筛选）
                COL_MSG_TIME + " ASC"     // 排序方式
        );
    }




    // 帖子相关方法
    public long addPost(String title, String content, String author, String time) {
        SQLiteDatabase db = getWritableDatabase();
        //来自public android.database.sqlite.SQLiteDatabase getWritableDatabase()
        //获取可写入的数据库连接（无则创建）
        ContentValues values = new ContentValues();
        //创建ContentValues的对象，这是安卓特有结构
        values.put(COL_POST_TITLE, title);
        values.put(COL_POST_CONTENT, content);
        values.put(COL_POST_AUTHOR, author);
        values.put(COL_POST_TIME, time);
        long id = db.insert(TABLE_POSTS, null, values);
        db.close();
        return id;
    }

    // 帖子评论相关方法
    public long addMessageWithPostId(String username, String content, String time, long postId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MSG_USER, username);
        values.put(COL_MSG_CONTENT, content);
        values.put(COL_MSG_TIME, time);
        values.put(COL_MSG_POST_ID, postId);
        long id = db.insert(TABLE_MESSAGES, null, values);
        db.close();
        return id;
    }

    // 用户注册
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, username);
        values.put(COL_USER_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // 验证登录
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID},
                COL_USER_NAME + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // 获取所有帖子
    public Cursor getAllPosts() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_POSTS, null, null, null, null, null, COL_POST_TIME + " DESC");
    }

    // 根据帖子ID获取评论
    public Cursor getMessagesByPostId(long postId) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_MESSAGES, null,
                COL_MSG_POST_ID + "=?",
                new String[]{String.valueOf(postId)},
                null, null, COL_MSG_TIME + " ASC");
    }

    // 用户信息相关
    public Cursor getUserInfo() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_USERS, new String[]{COL_USER_NAME, COL_USER_EMAIL, COL_USER_PHONE},
                null, null, null, null, null);
    }

    public int updateUserInfo(String name, String email, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PHONE, phone);
        int rows = db.update(TABLE_USERS, values, COL_USER_NAME + "=?", new String[]{name});
        db.close();
        return rows;
    }

    public long addUserInfo(String name, String email, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PHONE, phone);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    public int clearUserInfo() {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_USERS, null, null);
        db.close();
        return rows;
    }

    // 商品相关
    public long addProduct(String name, double price, String desc) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, name);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_DESC, desc);
        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result;
    }

    public Cursor getProducts() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_PRODUCTS, null, null, null, null, null, null);
    }

    // 健康记录相关方法
    public long addHealthRecord(String date, double weight, double temp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_HEALTH_DATE, date);
        values.put(COL_HEALTH_WEIGHT, weight);
        values.put(COL_HEALTH_TEMP, temp);
        long result = db.insert(TABLE_HEALTH, null, values);
        db.close();
        return result;
    }

    public Cursor getHealthRecords() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_HEALTH, null, null, null, null, null, COL_HEALTH_DATE + " DESC");
    }

    public int deleteHealthRecord(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_HEALTH, COL_HEALTH_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}