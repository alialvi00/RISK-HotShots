import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class RiskView extends JFrame implements RiskListener{

    private RiskMap m;

    private JButton startGame;
    private JButton loadGame;
    private JButton loadCustomMap;
    private JButton attackButton;
    private JButton passTurn;
    private JButton confirmButton;
    private JButton maneuverButton;

    private JTextArea consoleUpdate;

    private JLabel askForCountPlayers;
    private JLabel askName;
    private JLabel chooseCountry;
    private JLabel adjacentCountry;
    private JLabel currentPlayerInfo;

    private String[] playerNum= {"2","3","4","5","6"};
    private ArrayList<String> playerNames;
    private JComboBox playerCountList;

    private JComboBox computerOrPlayer1;
    private JComboBox computerOrPlayer2;
    private JComboBox computerOrPlayer3;
    private JComboBox computerOrPlayer4;
    private JComboBox computerOrPlayer5;
    private JComboBox computerOrPlayer6;

    private ArrayList<Boolean> isAI;

    private String[] computerOrPlayer = {"AI","Player"};

    private JPanel mainCont;
    private JPanel newGameScene;
    private JPanel mainGameScene;

    private JPanel askCount;
    private JPanel consolePanel;
    private JPanel mapPanel;
    private JPanel actionPanel;
    private JPanel infoPanel;

    private JPanel northAmericaPanel;
    private JPanel southAmericaPanel;
    private JPanel europePanel;
    private JPanel africaPanel;
    private JPanel asiaPanel;
    private JPanel australiaPanel;

    private JScrollPane consoleMove;
    private JScrollPane mapScroll;
    private JScrollPane selectedCountryScrollPane;
    private JScrollPane connectedCountryScrollPane;

    private JList<Country> selectedCountries;
    private JList<String> connectedCountries;

    AdjListController adjListController;
    ListController listController;
    FortifyController fortifyController;
    ManeuverController maneuverController;
    SaveLoadController saveLoadController;
    FilePickController filePickController;

    DefaultListModel<Country> ownedCountriesModel;
    DefaultListModel<String> adjacentCountriesModel;

    private JTextField p1;
    private JTextField p2;
    private JTextField p3;
    private JTextField p4;
    private JTextField p5;
    private JTextField p6;
    private ArrayList<JTextField> nameFields;

    private CardLayout c1;
    private BorderLayout b1;
    private GridBagLayout mainGameLayout;
    private GridBagLayout consoleLayout;
    private GridBagLayout actionLayout;
    private GridBagLayout infoLayout;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem saveOption;
    private JMenuItem quitOption;

    private GridBagConstraints a1;
    private GridBagConstraints a2;

    private ImageIcon mapImage;

    private DefaultCaret style = new DefaultCaret();

    private boolean isManeuverMode;
    private boolean isCustomMap;

    RiskController rController;
    RiskModel rm;


    public RiskView(){

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        //initializing the controller and the model
        rm = new RiskModel();
        rController = new RiskController(rm, this);

        isManeuverMode = false;
        isCustomMap = false;

        PlayerController pController = new PlayerController(this);

        rm.addView(this);

        b1 = new BorderLayout();
        c1 = new CardLayout(5,5);

        mainGameLayout = new GridBagLayout();
        consoleLayout = new GridBagLayout();
        actionLayout = new GridBagLayout();
        infoLayout = new GridBagLayout();

        passTurn = new JButton("Pass Turn");

        a1 = new GridBagConstraints();
        a1.fill = GridBagConstraints.BOTH;

        a2 = new GridBagConstraints();

        startGame = new JButton("Start the fun!");
        startGame.setEnabled(true);

        //start button listener
        startGame.addActionListener(rController);

        //initiating the List listener
        adjListController = new AdjListController(this);
        listController = new ListController(rm, this);
        fortifyController = new FortifyController(rm, this);
        maneuverController = new ManeuverController(rm, this);
        saveLoadController = new SaveLoadController(rm,this);
        filePickController = new FilePickController(rm, this);

        loadGame = new JButton("Load the saved game");
        loadGame.setActionCommand("load");
        loadGame.addActionListener(saveLoadController);


        //button for loading map
        loadCustomMap = new JButton("Load Custom Map");
        loadCustomMap.setEnabled(true);
        loadCustomMap.addActionListener(filePickController);
        //mapImage = new ImageIcon(getClass().getClassLoader().getResource("mapRisk.png"));

        attackButton = new JButton("Attack");

        computerOrPlayer1 = new JComboBox(computerOrPlayer);
        computerOrPlayer2 = new JComboBox(computerOrPlayer);
        computerOrPlayer3 = new JComboBox(computerOrPlayer);
        computerOrPlayer4 = new JComboBox(computerOrPlayer);
        computerOrPlayer5 = new JComboBox(computerOrPlayer);
        computerOrPlayer6 = new JComboBox(computerOrPlayer);

        isAI = new ArrayList<>();

        northAmericaPanel = new JPanel();
        southAmericaPanel = new JPanel();
        europePanel = new JPanel();
        africaPanel = new JPanel();
        asiaPanel = new JPanel();
        australiaPanel = new JPanel();

        mapScroll = new JScrollPane(new JLabel(mapImage));


        mainCont = new JPanel(c1);
        newGameScene = new JPanel(b1);
        mainGameScene = new JPanel(mainGameLayout);
        consolePanel = new JPanel(consoleLayout);
        actionPanel = new JPanel(actionLayout);
        infoPanel = new JPanel(infoLayout);


        mapPanel = new JPanel();

        consoleUpdate = new JTextArea();

        askCount = new JPanel(new GridBagLayout());

        p1 = new JTextField("Napoleon",3);
        p2 = new JTextField("Columbus",3);
        p3 = new JTextField(3);
        p4 = new JTextField(3);
        p5 = new JTextField(3);
        p6 = new JTextField(3);

        nameFields = new ArrayList<>();

        nameFields.add(p1);
        nameFields.add(p2);
        nameFields.add(p3);
        nameFields.add(p4);
        nameFields.add(p5);
        nameFields.add(p6);

        p1.setFont(new Font("SansSerif", Font.BOLD, 14));
        p2.setFont(new Font("SansSerif", Font.BOLD, 14));
        p3.setFont(new Font("SansSerif", Font.BOLD, 14));
        p4.setFont(new Font("SansSerif", Font.BOLD, 14));
        p5.setFont(new Font("SansSerif", Font.BOLD, 14));
        p6.setFont(new Font("SansSerif", Font.BOLD, 14));

        p1.setEditable(true);
        p2.setEditable(true);
        p3.setEditable(false);
        p4.setEditable(false);
        p5.setEditable(false);
        p6.setEditable(false);

        newGameScene.setBorder(new EmptyBorder(6,6,6,6));
        mainGameScene.setBorder(new EmptyBorder(6,6,6,6));

        mainCont.add(newGameScene,"startScene");
        mainCont.add(mainGameScene,"secondScene");

        newGameScene.add(askCount,BorderLayout.CENTER);

        playerNames = new ArrayList<>();
        askName = new JLabel("Enter the player names");
        playerCountList = new JComboBox(playerNum);
        playerCountList.addItemListener(pController);;
        askForCountPlayers = new JLabel("How many players? ");

        newGameScene.setBackground(Color.BLACK);
        askCount.setBackground(new Color(144,238,144));

        initializeStart();
        c1.show(mainCont, "startScene");

        mainGameScene.setBackground(Color.black);
        startBackgroundMusic(getClass().getResource("BackgroundMusic.wav"));

        this.add(mainCont);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    public void initializeStart(){

        this.setTitle("Welcome to RISK: GLOBAL DOMINATION");
        this.setSize(new Dimension(450,400));
        this.setResizable(false);

        a1.insets = new Insets(6,6,6,6);
        a1.gridx = 0;
        a1.gridy = 0;
        a1.anchor = GridBagConstraints.NORTHWEST;
        askCount.add(askForCountPlayers, a1);

        a1.gridx = 0;
        a1.gridy = 1;
        a1.anchor = GridBagConstraints.NORTHWEST;
        a1.weightx = 1;
        a1.weighty = 1;
        askCount.add(playerCountList,a1);

        a1.gridx = 1;
        a1.gridy = 0;
        a1.anchor = GridBagConstraints.NORTHWEST;
        a1.weighty = 1;
        a1.weightx = 1;
        askCount.add(askName,a1);

        a1.gridx = 1;
        a1.gridy = 1;
        a1.anchor = GridBagConstraints.NORTHWEST;
        a1.weightx = 1;
        a1.weighty = 1;
        askCount.add(p1,a1);
        a1.gridy = 2;
        askCount.add(p2,a1);
        a1.gridy = 3;
        askCount.add(p3,a1);
        a1.gridy = 4;
        askCount.add(p4,a1);
        a1.gridy = 5;
        askCount.add(p5,a1);
        a1.gridy = 6;
        askCount.add(p6,a1);

        a1.gridx = 2;
        a1.gridy = 1;
        a1.anchor = GridBagConstraints.NORTHWEST;
        a1.weightx = 1;
        a1.weighty = 1;
        askCount.add(computerOrPlayer1,a1);
        a1.gridy = 2;
        askCount.add(computerOrPlayer2,a1);
        a1.gridy = 3;
        askCount.add(computerOrPlayer3,a1);
        a1.gridy = 4;
        askCount.add(computerOrPlayer4,a1);
        a1.gridy = 5;
        askCount.add(computerOrPlayer5,a1);
        a1.gridy = 6;
        askCount.add(computerOrPlayer6,a1);

        a1.gridx = 0;
        a1.gridy = 2;
        askCount.add(startGame, a1);

        a1.gridx = 0;
        a1.gridy = 3;
        askCount.add(loadCustomMap, a1);

        a1.gridx = 0;
        a1.gridy = 4;
        askCount.add(loadGame, a1);
    }

    public void startBackgroundMusic(URL musicPath){

        try{
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-5.0f);
        }
        catch(Exception e){
            System.out.println("Music file not found, no music to be played :(");
        }
    }

    public void playSoundEffect(URL soundEffect){
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundEffect);
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace( );
        }
    }

    public int getPlayerCount(){

        int index = playerCountList.getSelectedIndex();
        switch(index){
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 5;
            case 4:
                return 6;
        }
        return -1;
    }

    public void setNameFields(int playerCount, boolean enable, boolean disable){

        if(playerCount == 2) {

            p1.setEditable(enable);
            p2.setEditable(enable);
            p1.setText("Napoleon");
            p2.setText("Columbus");

            p3.setEditable(disable);
            p4.setEditable(disable);
            p5.setEditable(disable);
            p6.setEditable(disable);

            p3.setText("");
            p4.setText("");
            p5.setText("");
            p6.setText("");
        }
        if(playerCount == 3){

            p1.setEditable(enable);
            p2.setEditable(enable);
            p3.setEditable(enable);

            p1.setText("Napoleon");
            p2.setText("Columbus");
            p3.setText("Caesar");

            p4.setEditable(disable);
            p5.setEditable(disable);
            p6.setEditable(disable);
            p4.setText("");
            p5.setText("");
            p6.setText("");
        }
        if(playerCount == 4){
            p1.setEditable(enable);
            p2.setEditable(enable);
            p3.setEditable(enable);
            p4.setEditable(enable);

            p1.setText("Napoleon");
            p2.setText("Columbus");
            p3.setText("Caesar");
            p4.setText("Gustavus");

            p5.setEditable(disable);
            p6.setEditable(disable);
            p5.setText("");
            p6.setText("");
        }
        if(playerCount == 5){
            p1.setEditable(enable);
            p2.setEditable(enable);
            p3.setEditable(enable);
            p4.setEditable(enable);
            p5.setEditable(enable);

            p1.setText("Napoleon");
            p2.setText("Columbus");
            p3.setText("Caesar");
            p4.setText("Gustavus");
            p5.setText("Genghis");

            p6.setEditable(disable);
            p6.setText("");
        }
        if(playerCount == 6){
            p1.setEditable(enable);
            p2.setEditable(enable);
            p3.setEditable(enable);
            p4.setEditable(enable);
            p5.setEditable(enable);
            p6.setEditable(enable);

            p1.setText("Napoleon");
            p2.setText("Columbus");
            p3.setText("Caesar");
            p4.setText("Gustavus");
            p5.setText("Genghis");
            p6.setText("Zulqarnain");
        }
    }

    public void setPlayerNames(){
        switch (getPlayerCount()) {
            case 3:
                playerNames.add(p1.getText());
                playerNames.add(p2.getText());
                playerNames.add(p3.getText());

            case 4:
                playerNames.add(p1.getText());
                playerNames.add(p2.getText());
                playerNames.add(p3.getText());
                playerNames.add(p4.getText());

            case 5:
                playerNames.add(p1.getText());
                playerNames.add(p2.getText());
                playerNames.add(p3.getText());
                playerNames.add(p4.getText());
                playerNames.add(p5.getText());

            case 6:
                playerNames.add(p1.getText());
                playerNames.add(p2.getText());
                playerNames.add(p3.getText());
                playerNames.add(p4.getText());
                playerNames.add(p5.getText());
                playerNames.add(p6.getText());

            default:
                playerNames.add(p1.getText());
                playerNames.add(p2.getText());
        }
    }

    public ArrayList<String> getPlayerNames(){
        return playerNames;
    }

    public void setPlayerType(){

        switch (getPlayerCount()) {
            case 3:
                isAI.add(computerOrPlayer1.getSelectedIndex()==0);
                isAI.add(computerOrPlayer2.getSelectedIndex()==0);
                isAI.add(computerOrPlayer3.getSelectedIndex()==0);

            case 4:
                isAI.add(computerOrPlayer1.getSelectedIndex()==0);
                isAI.add(computerOrPlayer2.getSelectedIndex()==0);
                isAI.add(computerOrPlayer3.getSelectedIndex()==0);
                isAI.add(computerOrPlayer4.getSelectedIndex()==0);

            case 5:
                isAI.add(computerOrPlayer1.getSelectedIndex()==0);
                isAI.add(computerOrPlayer2.getSelectedIndex()==0);
                isAI.add(computerOrPlayer3.getSelectedIndex()==0);
                isAI.add(computerOrPlayer4.getSelectedIndex()==0);
                isAI.add(computerOrPlayer5.getSelectedIndex()==0);

            case 6:
                isAI.add(computerOrPlayer1.getSelectedIndex()==0);
                isAI.add(computerOrPlayer2.getSelectedIndex()==0);
                isAI.add(computerOrPlayer3.getSelectedIndex()==0);
                isAI.add(computerOrPlayer4.getSelectedIndex()==0);
                isAI.add(computerOrPlayer5.getSelectedIndex()==0);
                isAI.add(computerOrPlayer6.getSelectedIndex()==0);

            default:
                isAI.add(computerOrPlayer1.getSelectedIndex()==0);
                isAI.add(computerOrPlayer2.getSelectedIndex()==0);
        }
    }
    public ArrayList<Boolean> getPlayerType(){
        return isAI;
    }


    public void switchView(){
        c1.show(mainCont,"secondScene");
    }

    public void startNewGame(){

        this.setSize(new Dimension(1980,1020));

        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        menu = new JMenu("Options");
        menuBar.add(menu);

        saveOption = new JMenuItem("Save the current game");
        saveOption.setActionCommand("save");
        saveOption.addActionListener(saveLoadController);

        quitOption = new JMenuItem("Awh, ur quitting the game :( ");
        quitOption.setEnabled(true);
        quitOption.setActionCommand("quit");;
        quitOption.addActionListener(rController);

        menu.add(saveOption);
        menu.add(quitOption);
        this.setJMenuBar(menuBar);

        a2.fill = GridBagConstraints.BOTH;
        a2.anchor = GridBagConstraints.LINE_START;
        a2.insets = new Insets(5,5,5,5);
        a2.weightx = 0.5;
        a2.weighty = 0.5;
        a2.gridx = 0;
        a2.gridy = 0;
        mainGameScene.add(addConsole(),a2);

        a2.fill = GridBagConstraints.BOTH;
        a2.anchor = GridBagConstraints.CENTER;
        a2.insets = new Insets(5, 5, 5, 5);
        a2.weightx = 8;
        a2.weighty = 0.5;
        a2.gridx = 1;
        a2.gridy = 0;
        mainGameScene.add(addMap(),a2);

        a2.fill = GridBagConstraints.BOTH;
        a2.anchor = GridBagConstraints.LINE_END;
        a2.insets = new Insets(5, 5, 5, 5);
        a2.weightx = 0.5;
        a2.weighty = 0.5;
        a2.gridx = 2;
        a2.gridy = 0;
        mainGameScene.add(addAction(),a2);

        a2.fill = GridBagConstraints.BOTH;
        a2.anchor = GridBagConstraints.LINE_END;
        a2.insets = new Insets(5, 5, 5, 5);
        a2.weightx = 0.5;
        a2.weighty = 0.5;
        a2.gridx = 3;
        a2.gridy = 0;
        mainGameScene.add(setInfoPanel(),a2);

    }

    public JPanel addConsole(){

        consolePanel.setPreferredSize(new Dimension(300,980));
        GridBagConstraints a3 = new GridBagConstraints();

        System.setOut(new PrintStream(new ChangeOutputStream(consoleUpdate)));
        consoleUpdate.setFocusable(false);
        consoleUpdate.setLineWrap(true);
        consoleUpdate.setWrapStyleWord(true);
        style = (DefaultCaret)consoleUpdate.getCaret();
        style.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consoleMove = new JScrollPane(consoleUpdate);

        a3.fill = GridBagConstraints.BOTH;
        a3.insets = new Insets(5, 5, 5, 5);
        a3.weightx = 0.5;
        a3.weighty = 14;
        a3.gridx = 0;
        a3.gridy = 0;
        consolePanel.add(consoleMove,a3);
        return consolePanel;

    }

    public JPanel addMap(){

        mapPanel.setLayout(new GridLayout(1,1,5,5));
        mapScroll.setPreferredSize(new Dimension(1080,980));

        mapPanel.add(mapScroll);
        return mapPanel;
    }

    public void addSelectedCountryListener(ListSelectionListener selected){}

    public JPanel addAction(){

        actionPanel.setPreferredSize(new Dimension(200,980));
        actionPanel.setLayout(actionLayout);
        actionPanel.setBackground(new Color(135,206,235));

        chooseCountry = new JLabel("Choose a country");
        adjacentCountry = new JLabel("Adjacent Countries");


        currentPlayerInfo = new JLabel(" ");
        currentPlayerInfo.setBorder(BorderFactory.createLineBorder(new Color(139,0,139), 5));

        confirmButton = new JButton("Confirm Maneuver");
        confirmButton.setBackground(new Color(66, 245, 126));
        confirmButton.addActionListener(maneuverController);
        confirmButton.setActionCommand("confirm");
        confirmButton.setEnabled(false);

        maneuverButton = new JButton("Maneuver Mode");
        maneuverButton.setBackground(new Color(254,216,177));
        maneuverButton.setActionCommand("maneuverEnable");
        maneuverButton.addActionListener(maneuverController);
        maneuverButton.setEnabled(false);

        attackButton = new JButton("Attack!!!");
        attackButton.setBackground(Color.pink);

        passTurn = new JButton("Meh, Pass");
        passTurn.setBackground(Color.ORANGE);

        //adding controller to attack button
        AttackController attackController = new AttackController(rm, this);
        attackButton.addActionListener(attackController);
        attackButton.setEnabled(false);

        //adding the controller to the pass button
        PassController passController = new PassController(this, rm);
        passTurn.addActionListener(passController);
        disablePassButton();

        ownedCountriesModel = new DefaultListModel<>();
        adjacentCountriesModel = new DefaultListModel<>();

        selectedCountries = new JList(ownedCountriesModel);
        selectedCountries.setBackground(Color.gray);

        selectedCountries.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        selectedCountries.setLayoutOrientation(JList.VERTICAL_WRAP);
        selectedCountries.setVisibleRowCount(21);

        connectedCountries = new JList(adjacentCountriesModel);
        connectedCountries.setBackground(Color.gray);

        connectedCountries.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        connectedCountries.setLayoutOrientation(JList.VERTICAL_WRAP);
        connectedCountries.setVisibleRowCount(6);

        selectedCountryScrollPane = new JScrollPane(selectedCountries);
        connectedCountryScrollPane = new JScrollPane(connectedCountries);

        //adding the action listener to the owned countries JList
        selectedCountries.addListSelectionListener(fortifyController);

        GridBagConstraints a4 = new GridBagConstraints();

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 0;
        actionPanel.add(currentPlayerInfo, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 4;
        actionPanel.add(chooseCountry, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 10;
        a4.gridx = 0;
        a4.gridy = 5;
        actionPanel.add(selectedCountryScrollPane, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 6;
        actionPanel.add(confirmButton, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 7;
        actionPanel.add(adjacentCountry, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 10;
        a4.gridx = 0;
        a4.gridy = 8;
        actionPanel.add(connectedCountryScrollPane, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 9;
        actionPanel.add(attackButton, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 10;
        actionPanel.add(maneuverButton, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 11;
        actionPanel.add(passTurn, a4);

        return actionPanel;
    }

    public JPanel setInfoPanel(){

        infoPanel.setPreferredSize(new Dimension(320,980));

        infoPanel.setBackground(Color.gray);

        northAmericaPanel.setBackground(Color.white);
        southAmericaPanel.setBackground(Color.white);
        asiaPanel.setBackground(Color.white);
        africaPanel.setBackground(Color.white);
        australiaPanel.setBackground(Color.white);
        europePanel.setBackground(Color.white);

        //repeat the code below for all continent panels : @Hassan
        northAmericaPanel.setPreferredSize(new Dimension(320,100));
        northAmericaPanel.setLayout(new GridLayout(5,2,5,5));

        asiaPanel.setPreferredSize(new Dimension(320,100));
        asiaPanel.setLayout(new GridLayout(5,2,5,5));

        africaPanel.setPreferredSize(new Dimension(320,100));
        africaPanel.setLayout(new GridLayout(5,2,5,5));

        europePanel.setPreferredSize(new Dimension(320,100));
        europePanel.setLayout(new GridLayout(5,2,5,5));

        southAmericaPanel.setPreferredSize(new Dimension(320,100));
        southAmericaPanel.setLayout(new GridLayout(5,2,5,5));

        australiaPanel.setPreferredSize(new Dimension(320,100));
        australiaPanel.setLayout(new GridLayout(5,2,5,5));

        GridBagConstraints a5 = new GridBagConstraints();

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 0;
        infoPanel.add(new JLabel("North America"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 1;
        infoPanel.add(northAmericaPanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 2;
        infoPanel.add(new JLabel("South America"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 3;
        infoPanel.add(southAmericaPanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 4;
        infoPanel.add(new JLabel("Europe"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 5;
        infoPanel.add(europePanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 6;
        infoPanel.add(new JLabel("Africa"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 7;
        infoPanel.add(africaPanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 8;
        infoPanel.add(new JLabel("Asia"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 9;
        infoPanel.add(asiaPanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 10;
        infoPanel.add(new JLabel("Australia"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 11;
        infoPanel.add(australiaPanel, a5);

        return infoPanel;
    }

    public void updateCountriesJlist(Player p) {
        if(!ownedCountriesModel.isEmpty()){
            ownedCountriesModel.removeAllElements();
        }
        for (Country c : p.getCountries()) {
            ownedCountriesModel.addElement(c);
        }
        updatePlayerJLabel(p);
    }

    public Country getOriginCountry(){
        return selectedCountries.getSelectedValue();
    }

    public String getDestinationCountry(){
        attackButton.setEnabled(true);
        return connectedCountries.getSelectedValue();
    }

    public void clearSelection(){
        selectedCountries.clearSelection();
        connectedCountries.clearSelection();
        adjacentCountriesModel.removeAllElements();
    }

    /**
     * this funtion will take in the maximum number of troops a country can attack with
     * then will allow the user to choose the number of troops
     * @param maxTroops maximum number of troops that can be used
     * @return the number of attacking troops
     */
    public int getAttackingTroops(int maxTroops){
        Integer[] options = new Integer[maxTroops];

        for(int i = 0; i < maxTroops; i++){
            options[i] = i + 1;
        }

        int choice = JOptionPane.showOptionDialog(this, "Choose the number of troops you would like to attack with: ",
                "Attack!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        return choice + 1;

    }

    /**
     * this funtion will take in the maximum number of troops a country can defend with
     * then will allow the user to choose the number of troops
     * @param maxTroops maximum number of troops that can be used
     * @return the number of defending troops
     */
    public int getDefendingTroops(int maxTroops, Player player, Country defendingCountry){
        Integer[] options = new Integer[maxTroops];

        for(int i = 0; i < maxTroops; i++){
            options[i] = i + 1;
        }


        String message = player.getName() + ", " + getDestinationCountry() + " is being attacked! Choose the number of troops you would like to defend with";
        if(rm.getCurrentPlayer().getIsAI() && defendingCountry != null){
            message = player.getName() + ", " + defendingCountry.toString() + " is being attacked! Choose the number of troops you would like to defend with";
        }

        int choice = JOptionPane.showOptionDialog(this, message,
                "Defend!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        clearSelection();

        return choice + 1;

    }

    /**
     * gets the amount of troops the user would like to fortify with using JOPtionPane
     * @param availableTroops int
     * @return int
     */
    public int  getEnforcementAmount(int availableTroops){

        Integer[] options = new Integer[availableTroops];


        for (int i = 0; i < availableTroops; i++) {
            options[i] = i + 1;
        }

        String message = "How many troops would you like to fortify with?";


        int choice = JOptionPane.showOptionDialog(this, message,
                "Fortify your country!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        clearSelection();

        return choice + 1;
    }

    /**
     * this funtion will take in the maximum number of troops a country can maneuver with
     * then will allow the user to choose the number of troops
     * @param maxTroops maximum number of troops that can be used
     * @return the number of maneuvering troops
     */
    public int getManeuverTroops(int maxTroops){
        Integer[] options = new Integer[maxTroops];

        for(int i = 0; i < maxTroops; i++){
            options[i] = i + 1;
        }

        int choice = JOptionPane.showOptionDialog(this, "Choose the number of troops you would like to maneuver",
                "Maneuver",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        return choice + 1;

    }

    //enables the attack button
    public void enableAttack(){
        attackButton.setEnabled(true);
    }

    //disables the attack button
    public void disableAttack(){
        attackButton.setEnabled(false);
    }


    /**
     * adds the initial map to the info panel, displays country, owner, troops
     */
    @Override
    public void handleInitialMap(MapEvent m) {

        RiskModel model = (RiskModel) m.getSource();

        for (Player p : m.getPlayerList()) {
            for (Country c : p.getCountries()) {
                JLabel countryLabel = new JLabel(c + ", " + p.getName() + ", " + p.getPlayerData().get(c));
                if(c.getContinent().getName().equals("North America")){
                    northAmericaPanel.add(countryLabel);
                } else if(c.getContinent().getName().equals("Europe")){
                    europePanel.add(countryLabel);
                } else if(c.getContinent().getName().equals("Africa")){
                    africaPanel.add(countryLabel);
                } else if(c.getContinent().getName().equals("Australia")){
                    australiaPanel.add(countryLabel);
                } else if(c.getContinent().getName().equals("Asia")){
                    asiaPanel.add(countryLabel);
                }else{
                    southAmericaPanel.add(countryLabel);
                }
            }
        }
        updatePlayerJLabel(model.getCurrentPlayer());

        System.out.println("");
        System.out.println("It is " + model.getCurrentPlayer().getName() + "'s turn");
        updateCountriesJlist(model.getCurrentPlayer());
    }

    /**
     * updates the infoPanel after each update to countries
     */
    @Override
    public void handleMapChange(MapEvent m){

        RiskModel model = (RiskModel) m.getSource();
        updateCountriesJlist(model.getCurrentPlayer());

        //updating the map (infoPanel)
        northAmericaPanel.removeAll();
        asiaPanel.removeAll();
        southAmericaPanel.removeAll();
        africaPanel.removeAll();
        australiaPanel.removeAll();
        europePanel.removeAll();

        for (Player p : m.getPlayerList()) {
            for (Country c : p.getCountries()) {
                JLabel countryLabel = new JLabel(c + ", " + p.getName() + ", " + p.getPlayerData().get(c));
                if(c.getContinent().getName().equals("North America")){
                    northAmericaPanel.add(countryLabel);
                } else if(c.getContinent().getName().equals("Europe")){
                    europePanel.add(countryLabel);
                } else if(c.getContinent().getName().equals("Africa")){
                    africaPanel.add(countryLabel);
                } else if(c.getContinent().getName().equals("Australia")){
                    australiaPanel.add(countryLabel);
                } else if(c.getContinent().getName().equals("Asia")){
                    asiaPanel.add(countryLabel);
                }else{
                    southAmericaPanel.add(countryLabel);
                }
            }
        }
        updatePlayerJLabel(model.getCurrentPlayer());

        revalidate();
        repaint();
    }

    public void changeModel(RiskModel rm){

        this.rm = rm;
        listController.changeModel(rm);
        fortifyController.changeModel(rm);
        maneuverController.changeModel(rm);
        saveLoadController.changeModel(rm);
        filePickController.changeModel(rm);
    }

    /**
     * updates the Jlist of adjacent countries of the selected country, only enemy countries appear
     */
    @Override
    public void handleAdjacentList(ListEvent l){
        ArrayList<String> countryList = l.getAdjacentList();

        if(!adjacentCountriesModel.isEmpty()){
            adjacentCountriesModel.removeAllElements();
        }
        for (String c : countryList) {
            adjacentCountriesModel.addElement(c);
        }
    }

    /**
     * ends the game once only one player remains
     */
    @Override
    public void handleEndGame(MapEvent m){
        JOptionPane.showMessageDialog(this,  m.getPlayerList().get(0).getName() + " has won!");
        System.exit(0);
    }

    /**
     * adds the event listener to the adjacent countries jlist
     */
    public void addAdjListener(){
        connectedCountries.addListSelectionListener(adjListController);
    }

    /**
     * removes the event listener to the adjacent countries jlist
     */
    public void removeAdjListener(){
        connectedCountries.removeListSelectionListener(adjListController);
    }

    /**
     * removes the event listener to the owned countries jlist
     */
    public void removeOwnedListListener(){
        selectedCountries.removeListSelectionListener(listController);
    }

    /**
     * changes the JList of owned countries to be in fortify mode
     */
    public void setFortifyMode(){
        selectedCountries.removeListSelectionListener(listController);
        selectedCountries.addListSelectionListener(fortifyController);
        disableAttack();
        disablePassButton();
        disableManeuverButton();
    }

    /**
     * sets the JList to normal mode after the player fortifies
     */
    public void setNormalMode(){
        selectedCountries.removeListSelectionListener(fortifyController);
        selectedCountries.addListSelectionListener(listController);
        addAdjListener();
        enablePassButton();
        enableManeuverButton();
    }

    /**
     * enables the pass button
     */
    public void enablePassButton(){
        passTurn.setEnabled(true);
    }
    /**
     * disables the pass button
     */
    public void disablePassButton(){
        passTurn.setEnabled(false);
    }

    /**
     * updates the JLabel showing the current player and available enforcements
     * @param currentPlayer type Player
     */

    public void updatePlayerJLabel(Player currentPlayer){
        currentPlayerInfo.setText(currentPlayer.getName() + ": " + currentPlayer.getAvailableEnforcement() + " available enforcement(s)");
    }


    /**
     * disables the maneuver button
     */
    public void disableManeuverButton(){
        maneuverButton.setEnabled(false);
    }

    /**
     * enables the maneuver button
     */
    public void enableManeuverButton(){
        maneuverButton.setEnabled(true);
    }

    /**
     * disables the confirm maneuver button
     */
    public void disableConfirmButton(){
        confirmButton.setEnabled(false);
    }

    /**
     * enables the confirm maneuver button
     */
    public void enableConfirmButton(){
        confirmButton.setEnabled(true);
    }

    /**
     * changes the buttons and behaviour of lists based on the mode
     */
    public void changeManeuverMode(boolean mode){
        if(mode){
            maneuverButton.setText("Exit maneuver mode");
            maneuverButton.setActionCommand("exitManeuver");
            isManeuverMode = true;
        }  else{
            maneuverButton.setText("Maneuver mode");
            maneuverButton.setActionCommand("maneuverEnable");
            isManeuverMode = false;
        }
    }

    /**
     * return true if the game is in maneuver mode, false otherwise
     */
    public boolean getMode(){
        return isManeuverMode;
    }

    /**
     * handles a new turn
     */
    public void handleNewTurn(MapEvent m){
        RiskModel model = (RiskModel) m.getSource();
        if(!model.getCurrentPlayer().getIsAI()){
            setFortifyMode();
        }
        handleMapChange(m);
        //enableManeuverButton();
        System.out.println("\nIt is " + model.getCurrentPlayer().getName() + "'s turn \n");
    }

    /**
     * allows the user to pick a json file as a map
     * must be in library\\Maps folder
     * @return
     */
    public String pickFile(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON","json");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getName();
        } else{
            return null;
        }
    }

    /**
     * displays a message if the map is not valid
     */
    public void fileError(){
        String warning = "Map is not vaild. Please look at the default map for reference.\n" +
        "Please insure that all areas are accessible and that there are no missing links";
        JOptionPane.showMessageDialog(this, warning, "Warning",
            JOptionPane.WARNING_MESSAGE);
    }

    /**
     * changes map type from custom or default
     * @param b
     */
    public void setMapType(boolean b){
        isCustomMap = b;
    }

    /**
     * type of map custom/default
     * @return true if custom
     */
    public boolean mapType(){
        return isCustomMap;
    }

    public static void main(String[] args) {
        new RiskView();
    }
}

