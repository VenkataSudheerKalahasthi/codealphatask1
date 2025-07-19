import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AIChatBot extends JFrame implements ActionListener {

    JTextArea chatArea;
    JTextField inputField;
    JButton sendButton, clearButton, exitButton, helpButton;

    HashMap<String, String> knowledgeBase;

    public AIChatBot() {
        setTitle("🤖 AI ChatBot");
        setSize(600, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fonts
        Font font = new Font("Arial", Font.PLAIN, 16);

        // Chat area
        chatArea = new JTextArea();
        chatArea.setFont(font);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField("Type your message here...");
        inputField.setFont(font);
        inputField.setForeground(Color.GRAY);

        // Clear placeholder text on focus
        inputField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals("Type your message here...")) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }
        });

        sendButton = new JButton("Send");
        sendButton.setFont(font);
        sendButton.addActionListener(this);
        inputField.addActionListener(this);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Utility buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clearButton = new JButton("Clear Chat");
        exitButton = new JButton("Exit");
        helpButton = new JButton("Help");

        clearButton.addActionListener(e -> chatArea.setText(""));
        exitButton.addActionListener(e -> System.exit(0));
        helpButton.addActionListener(e -> showHelp());

        controlPanel.add(clearButton);
        controlPanel.add(helpButton);
        controlPanel.add(exitButton);

        // Add input and control panels
        add(inputPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);

        // Load rules
        knowledgeBase = new HashMap<>();
        loadKnowledgeBase();

        appendMessage("🤖 Bot", "Hello! I’m your AI assistant. Ask me anything!");
    }

    void loadKnowledgeBase() {
        knowledgeBase.put("hello", "Hi there! How can I help you?");
        knowledgeBase.put("hi", "Hello! 😊 What do you want to know?");
        knowledgeBase.put("how are you", "I'm just a bunch of code, but I'm doing great!");
        knowledgeBase.put("what is ai", "AI stands for Artificial Intelligence — simulating human thinking by machines.");
        knowledgeBase.put("bye", "Goodbye! Have a great day!");
        knowledgeBase.put("thank you", "You're welcome!");
        knowledgeBase.put("your name", "I'm ChatBot 1.0, built with Java Swing.");
        knowledgeBase.put("joke", "Why did the programmer quit his job? Because he didn't get arrays (a raise)!");
        knowledgeBase.put("help", "You can try typing: hello, how are you, what is ai, tell me a joke, bye.");
    }

    String getBotResponse(String userInput) {
        String input = userInput.toLowerCase();

        for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
            if (input.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return "Sorry, I don't understand that yet. Try something else or click Help.";
    }

    void appendMessage(String sender, String message) {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        chatArea.append("[" + time + "] " + sender + ": " + message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength()); // Auto-scroll
    }

    void showHelp() {
        JOptionPane.showMessageDialog(this,
                "🆘 Example messages:\n" +
                        "- Hello\n- What is AI?\n- Tell me a joke\n- How are you?\n- Bye",
                "Help - ChatBot Commands",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userText = inputField.getText().trim();
        if (userText.isEmpty() || userText.equals("Type your message here...")) return;

        appendMessage("👤 You", userText);
        String botResponse = getBotResponse(userText);
        appendMessage("🤖 Bot", botResponse);

        inputField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AIChatBot().setVisible(true);
        });
    }
}
