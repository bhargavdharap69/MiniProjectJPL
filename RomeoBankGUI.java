import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class RomeoBankGUI {
    private final UserDAO userDAO = new UserDAO();
    private int currentUserAccountId = -1; // Will store the account ID of the current user
    private final Image logoImage = new ImageIcon("C:\\Users\\bharg\\Downloads\\RomeoBank Banking For The Young. (1).png").getImage(); // Path to your logo

    public static void main(String[] args) {
        // Apply FlatLaf Dark Theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("Button.arc", 20); // Rounded buttons
            UIManager.put("Component.focusWidth", 1); // Minimal focus border
            UIManager.put("defaultFont", new FontUIResource(new Font("SansSerif", Font.PLAIN, 14)));
        } catch (Exception e) {
            System.out.println("Failed to apply theme.");
        }
        new RomeoBankGUI().showWelcomeScreen();
    }

    private void drawLogo(Graphics g, JPanel panel) {
        int logoWidth = 70; // Increased logo width
        int logoHeight = 150; // Increased logo height
        g.drawImage(logoImage, 40, 50, logoWidth, logoHeight, panel); // Draw logo at top-left corner
    }

    private void showWelcomeScreen() {
        JFrame welcomeFrame = new JFrame();
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setUndecorated(true);
        welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // List of image paths
        List<String> imagePaths = List.of(
                "C:\\Users\\bharg\\Downloads\\6.wp7009320.jpg",
                "C:\\Users\\bharg\\Downloads\\wp7009269-marine-drive-wallpapers.jpg",
                "C:\\Users\\bharg\\Downloads\\wallpaperflare.com_wallpaper.jpg",
                "C:\\Users\\bharg\\Downloads\\download.jpeg",
                "C:\\Users\\bharg\\Downloads\\download (1).jpeg"
        );

        String selectedImagePath = imagePaths.get(new Random().nextInt(imagePaths.size()));

        JPanel welcomePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                ImageIcon backgroundIcon = new ImageIcon(selectedImagePath);
                Image backgroundImage = backgroundIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image

                drawLogo(g, this); // Draw the logo at top-left corner
            }
        };

        welcomePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("RomeoBank", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 190));
        titleLabel.setForeground(new Color(27, 139, 250));
        
        JLabel subtitleLabel = new JLabel("Banking For The Young.", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe Script", Font.BOLD, 90));
        subtitleLabel.setForeground(Color.WHITE);

        gbc.gridy = 0;
        welcomePanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        welcomePanel.add(subtitleLabel, gbc);

        welcomeFrame.add(welcomePanel);
        welcomeFrame.setVisible(true);

        // Fading effect Timer
        Timer fadeTimer = new Timer(100, new AbstractAction() {
            int alpha = 0;
            boolean increasing = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (increasing) {
                    alpha += 10;
                    if (alpha >= 255) {
                        alpha = 255;
                        increasing = false;
                    }
                } else {
                    alpha -= 10;
                    if (alpha <= 100) {
                        alpha = 100;
                        increasing = true;
                    }
                }

                titleLabel.setForeground(new Color(27, 139, 250, alpha));
                subtitleLabel.setForeground(new Color(255, 255, 255, alpha));
                welcomePanel.repaint();
            }
        });
        
        fadeTimer.start();

        // Transition Timer
        Timer transitionTimer = new Timer(5000, e -> {
            fadeTimer.stop(); // Stop the fade effect timer
            welcomeFrame.dispose(); // Close the welcome screen
            showMainMenu(); // Proceed to main menu
        });
        
        transitionTimer.setRepeats(false); // Ensure this runs only once
        transitionTimer.start();
    }

    private void showThankYouScreen() {
        JFrame thankYouFrame = new JFrame();
        thankYouFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thankYouFrame.setUndecorated(true);
        thankYouFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

         // List of image paths
         List<String> imagePaths=List.of(
             "C:\\Users\\bharg\\Downloads\\6.wp7009320.jpg",
             "C:\\Users\\bharg\\Downloads\\wp7009269-marine-drive-wallpapers.jpg",
             "C:\\Users\\bharg\\Downloads\\wallpaperflare.com_wallpaper.jpg",
             "C:\\Users\\bharg\\Downloads\\download.jpeg",
             "C:\\Users\\bharg\\Downloads\\download (1).jpeg"
         );

         String selectedImagePath=imagePaths.get(new Random().nextInt(imagePaths.size()));
         
         JPanel thankYouPanel=new JPanel() { 
             @Override protected void paintComponent(Graphics g) { 
                 super.paintComponent(g); 
                 ImageIcon backgroundIcon=new ImageIcon(selectedImagePath); 
                 Image backgroundImage=backgroundIcon.getImage(); 
                 g.drawImage(backgroundImage ,0 ,0 ,getWidth(), getHeight(), this); 
                 drawLogo(g,this); 
             } 
         }; 

         thankYouPanel.setLayout(new GridBagLayout()); 
         GridBagConstraints gbc=new GridBagConstraints(); 
         gbc.insets=new Insets(20 ,20 ,20 ,20 ); 

         JLabel thankYouLabel=new JLabel("Thank You For Banking With Us",SwingConstants.CENTER); 
         thankYouLabel.setFont(new Font("Garamond", Font.BOLD ,90)); 
         thankYouLabel.setForeground(new Color(255 ,255 ,255)); 

         JLabel comeAgainLabel=new JLabel("Do come again!",SwingConstants.CENTER); 
         comeAgainLabel.setFont(new Font("Segoe Script" ,Font.BOLD ,50)); 
         comeAgainLabel.setForeground(Color.WHITE);

         gbc.gridy=0; 
         thankYouPanel.add(thankYouLabel ,gbc); 

         gbc.gridy=1; 
         thankYouPanel.add(comeAgainLabel ,gbc);

         thankYouFrame.add(thankYouPanel); 
         thankYouFrame.setVisible(true);

         // Exit Timer
         Timer exitTimer=new Timer(5000,e -> { 
             thankYouFrame.dispose(); 
             System.exit(0); 
         }); 

         exitTimer.setRepeats(false); // Ensure this runs only once
         
         exitTimer.start(); 
     }

    private void showMainMenu() { 
           JFrame mainFrame=new JFrame("RomeoBank - Banking For The Young"); 
           mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

           // List of image paths
           List<String> imagePaths=List.of(
               "C:\\Users\\bharg\\Downloads\\6.wp7009320.jpg",
               "C:\\Users\\bharg\\Downloads\\wp7009269-marine-drive-wallpapers.jpg",
               "C:\\Users\\bharg\\Downloads\\wallpaperflare.com_wallpaper.jpg",
               "C:\\Users\\bharg\\Downloads\\download.jpeg",
               "C:\\Users\\bharg\\Downloads\\download (1).jpeg"
           );

           // Select a random image for the background
           String selectedImagePath=imagePaths.get(new Random().nextInt(imagePaths.size()));
           ImageIcon backgroundIcon=new ImageIcon(selectedImagePath);
           Image backgroundImage=backgroundIcon.getImage();

           JPanel mainMenuPanel=new JPanel() { 
               @Override protected void paintComponent(Graphics g) { 
                   super.paintComponent(g); 
                   // Draw the background image
                   g.drawImage(backgroundImage ,0 ,0 ,getWidth(), getHeight(), this); 
                   drawLogo(g,this);  
               }  
           }; 

           mainMenuPanel.setLayout(new GridBagLayout()); 

           GridBagConstraints gbc=new GridBagConstraints(); 
           gbc.insets=new Insets(10 ,10 ,10 ,10 ); 

           // Box panel for buttons
           JPanel boxPanel=new JPanel(); 
           boxPanel.setLayout(new GridLayout(0 ,1 ,10 ,10)); 

           boxPanel.setOpaque(false);  
           boxPanel.setBorder(BorderFactory.createEmptyBorder(20 ,40 ,20 ,40)); 

           // Title Label
           JLabel titleLabel=new JLabel("Menu",SwingConstants.CENTER);  
           titleLabel.setFont(new Font("Serif" ,Font.BOLD ,40));  
           titleLabel.setForeground(new Color(255 ,255 ,255));  
           titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
           boxPanel.add(titleLabel);

           JButton btnAddUser=new JButton("Open a New Account");  
           JButton btnViewBalance=new JButton("View Balance");  
           JButton btnDeposit=new JButton("Deposit Money");  
           JButton btnWithdraw=new JButton("Withdraw Money");  
           JButton btnTransferFunds=new JButton("Transfer Funds");  
           JButton btnQuit=new JButton("Quit");

           Dimension buttonSize=new Dimension(250 ,50 );  

           for(JButton button : List.of(btnAddUser ,btnViewBalance ,btnDeposit ,
                                         btnWithdraw ,btnTransferFunds ,
                                         btnQuit)) {  
               button.setPreferredSize(buttonSize );  
               button.setFont(new Font("SansSerif" ,Font.BOLD ,16));  
               button.setOpaque(false );  
               button.setContentAreaFilled(false );  
               button.setBorder(BorderFactory.createLineBorder(new Color(255 ,
                                                                       255 ,
                                                                       255 ,
                                                                       100),2,true));  
               button.setForeground(Color.WHITE );  
               button.setFocusPainted(false );  
               boxPanel.add(button );  
           }

           btnAddUser.addActionListener(e -> addUser());  
           btnViewBalance.addActionListener(e -> viewBalance());  
           btnDeposit.addActionListener(e -> deposit());  
           btnWithdraw.addActionListener(e -> withdraw());  
           btnTransferFunds.addActionListener(e -> transferFunds());  
           btnQuit.addActionListener(e -> quitApplication(mainFrame));

           gbc.gridy=0;   
           mainMenuPanel.add(boxPanel ,gbc);  

           mainFrame.add(mainMenuPanel ,BorderLayout.CENTER );   
           
           mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH );   
           
           mainFrame.setVisible(true );   
       }

     private void quitApplication(JFrame frame) {   
       int confirm=JOptionPane.showConfirmDialog(frame ,"Are you sure you want to exit?" ,"Exit Confirmation" ,
                                                  JOptionPane.YES_NO_OPTION );   
       if(confirm==JOptionPane.YES_OPTION){   
       frame.dispose();   
       showThankYouScreen();   
       }   
   }

   private void addUser() {   
       JTextField usernameField=new JTextField();   
       JTextField passwordField=new JPasswordField();   
       JTextField ageField=new JTextField();   
       JTextField emailField=new JTextField();

       Object[] fields={"Username:" ,usernameField ,"Password:" ,
                        passwordField ,"Age:" ,
                        ageField ,"Email:" ,
                        emailField};

       int option=JOptionPane.showConfirmDialog(null ,
                                                 fields ,"Add New User" ,
                                                 JOptionPane.OK_CANCEL_OPTION );

       if(option==JOptionPane.OK_OPTION){   
       String username=usernameField.getText();   
       String password=passwordField.getText();   
       int age=Integer.parseInt(ageField.getText());   
       String email=emailField.getText();

       userDAO.addUser(username,password ,age,email );   

       JOptionPane.showMessageDialog(null ,"User added successfully!");   
       }   
   }

   private boolean login() {   
       JTextField usernameField=new JTextField();   
       JPasswordField passwordField=new JPasswordField();

       Object[] fields={"Username:" ,usernameField ,"Password:" ,
                        passwordField};

       int option=JOptionPane.showConfirmDialog(null ,
                                                 fields ,"Login" ,
                                                 JOptionPane.OK_CANCEL_OPTION );

       if(option==JOptionPane.OK_OPTION){   
            String username=usernameField.getText();   
            String password=passwordField.getText();   

            int accountId=userDAO.authenticateUser(username,password);   

            if(accountId!=-1){   
                currentUserAccountId=accountId;   
                JOptionPane.showMessageDialog(null ,"Login successful!");   
                return true;   
            } else {   
                JOptionPane.showMessageDialog(null ,"Invalid username or password.");   
            }   
       }
       
       return false; // Login failed
   }

   private void viewBalance() {   
       if(currentUserAccountId==-1){   
            if(!login()) return;   
       }   

       double balance=userDAO.getBalance(currentUserAccountId);   

       if(balance!=-1){   
            JOptionPane.showMessageDialog(null ,"Your current balance is: ₹" + balance);   
       } else {   
            JOptionPane.showMessageDialog(null ,"Account not found.");   
       }   
   }

   private void transferFunds() {    
      if(currentUserAccountId==-1){    
          if(!login()) return;    
      }    

      JTextField recipientUsernameField=new JTextField();    
      JTextField amountField=new JTextField();

      Object[] fields={"Recipient Username:", recipientUsernameField,"Amount:", amountField};    

      int option=JOptionPane.showConfirmDialog(null,fields,"Fund Transfer",JOptionPane.OK_CANCEL_OPTION);

      if(option==JOptionPane.OK_OPTION){    
          String recipientUsername=recipientUsernameField.getText();    

          double amount;    

          try{    
              amount=Double.parseDouble(amountField.getText());    

              if(amount<=0){    
                  JOptionPane.showMessageDialog(null,"Amount should be greater than 0.");    
                  return;    
              }    

          }catch(NumberFormatException e){    
              JOptionPane.showMessageDialog(null,"Invalid amount entered.");    
              return;    
          }    

          int recipientAccountId=userDAO.getUserByUsername(recipientUsername);    

          if(recipientAccountId==-1){    
              JOptionPane.showMessageDialog(null,"Recipient not found.");    
              return;    
          }    

          boolean success=userDAO.transferFunds(currentUserAccountId,recipientAccountId,amount);    

          if(success){    
              JOptionPane.showMessageDialog(null,"Funds transferred successfully!");    
          }else{    
              JOptionPane.showMessageDialog(null,"Transfer failed. Insufficient balance or other error.");    
          }    
      }    
   }

   private void deposit() {    
      if(currentUserAccountId==-1){    
          if(!login()) return;    
      }    

      String amountString=JOptionPane.showInputDialog("Enter amount to deposit:");    

      try{    
          double amount=Double.parseDouble(amountString);    

          if(amount<=0){    
              JOptionPane.showMessageDialog(null,"Amount should be greater than 0.");    
              return;    
          }    

          userDAO.deposit(currentUserAccountId,amount);    
          JOptionPane.showMessageDialog(null,"Amount deposited successfully!");    

      }catch(NumberFormatException e){    
          JOptionPane.showMessageDialog(null,"Invalid amount entered.");    
      }    
   }

   private void withdraw() {    
      if(currentUserAccountId==-1){    
          if(!login()) return;    
      }    

      String amountString=JOptionPane.showInputDialog("Enter amount to withdraw:");    

      try{    
          double amount=Double.parseDouble(amountString);    

          if(amount<=0){    
              JOptionPane.showMessageDialog(null,"Amount should be greater than 0.");    
              return;    
          }    

          boolean success=userDAO.withdraw(currentUserAccountId,amount);    

          if(success){    
              JOptionPane.showMessageDialog(null,"Amount withdrawn successfully!");    
          }else{    
              JOptionPane.showMessageDialog(null,"Insufficient balance.");    
          }    

      }catch(NumberFormatException e){    
          JOptionPane.showMessageDialog(null,"Invalid amount entered.");    
      }    
   }
}
