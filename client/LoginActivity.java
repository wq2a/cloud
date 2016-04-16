package cloud.client;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;

public class LoginActivity extends Base {

    // views
    private JPanel main;
    private JLabel log;
    private JTextField username,password;
    private JButton loginbtn,registerbtn,test_loginbtn;
    private GridLayout gl;

    // init the view layout here
    private void setLayout(){
		
		gl = new GridLayout(10, 0);
		gl.setVgap(6);
		
		setTitle(" Cloud Login");
        
		main = new JPanel(gl);
        log = new JLabel("");
        username = new JTextField("",JTextField.CENTER);
        password = new JTextField("",JTextField.CENTER);
        loginbtn = new JButton("login");
        test_loginbtn = new JButton("testlogin");
        registerbtn = new JButton("register");

        
        log.setBorder(Config.REDBORDER);
      	username.setBorder(Config.BLACKBORDER);
      	password.setBorder(Config.BLACKBORDER);
        loginbtn.setBorder(Config.BLACKBORDER);
        test_loginbtn.setBorder(Config.BLACKBORDER);
        registerbtn.setBorder(Config.BLACKBORDER);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        main.add(Box.createRigidArea(new Dimension(1,0)));
        main.add(log);        
        main.add(username);
        main.add(password);
        main.add(loginbtn);
		main.add(registerbtn);
        main.add(test_loginbtn);
        /*main.addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent event){
                Component c = (Component)event.getSource();
                Dimension dim = c.getSize();
                //label.setText(dim.width+","+dim.height);
                // System.out.println(dim.width+","+dim.height);
            }
        });*/
        
        setContentPane(main);
        pack();
        setSize(300,500);
        setLocation(Config.SCREENDIM.width/2-getSize().width/2, Config.SCREENDIM.height/2-getSize().height/2);
        setVisible(true);
    }

    // register event lisener
    private void registerLisener(){
    	addWindowListener(this);
    	registerbtn.addActionListener(this);
        loginbtn.addActionListener(this);
        test_loginbtn.addActionListener(this);
    }

	// initialize the layout
	public void start(){
		setLayout();
		registerLisener();
	}

	/*
	 * click event here
	 */
	public void actionPerformed(ActionEvent e) {
        log.setText(e.getActionCommand());
        if(e.getActionCommand() == "login") {
        	Auth auth = Auth.getInstance();
        	Connection cnn = new Connection();
        	// get username and password and check. 
            // username:admin password:password
        	auth.setAccount(username.getText(),password.getText());
        	cnn.setRequestProperty("Auth",auth.toString());
			cnn.setRequestMethod(Connection.LOGIN,"POST");
            cnn.setTag(106);
			request(cnn);
        }else if(e.getActionCommand() == "testlogin"){
            Auth auth = Auth.getInstance();
            Connection cnn = new Connection();
            // get username and password and check. 
            // username:admin password:password
            auth.setAccount("admin","password");
            cnn.setRequestProperty("Auth",auth.toString());
            cnn.setRequestMethod(Connection.LOGIN,"POST");
            cnn.setTag(106);
            request(cnn);
        }
    }

	// receive data, still process in background thread, heavy work here
	public HashMap<String,String> preReceive(int requestID,int tag,HashMap<String,String> data){
		/* heavy work here*/
        // required to return data at the end
		return data;
	}

	// receive data which need to display, in UI thread
	public void receive(int requestID,int tag,HashMap<String,String> data){

		if(data != null){
			log.setText("ID:"+requestID+" Tag:"+tag+" Return Code:"+data.get("Status"));
            System.out.println("ID:"+requestID+" Tag:"+tag+" Return Code:"+data.get("Status"));

            if(tag == 106 && data.get("Status").equals(Connection.OK)){
                moveTo(TestActivity.class);
            }
		}
        
	}

    // test
    private void test(){
        /*
        //Auth auth = Auth.getInstance();
        Connection cnn = new Connection();

        // 1
        cnn = new Connection();
        cnn.setRequestMethod(Connection.LOGIN,"POST");
        Auth.getInstance().setAccount("admin","wrongpassword");
        cnn.setRequestProperty("Auth",Auth.getInstance().toString());
        request(cnn);

        // 2
        // get username and password and check. username:admin password:password
        cnn = new Connection();
        Auth.getInstance().setAccount("admin","password");
        cnn.setRequestProperty("Auth",Auth.getInstance().toString());
        cnn.setRequestMethod(Connection.LOGIN,"POST");
        // optional
        cnn.setTag(106);
        request(cnn);
        */
    }
}