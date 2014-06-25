package pizza;

import java.awt.*;
import java.awt.event.*;

/*
* руководство пользователя:
* 1. заполните поля - район, адрес, телефон, время
* 2. выберите пиццу из списка, нажмите Выбрать пиццу
* 3. повторите для выбора других или тех же товаров
* 4. по готовности нажмите Оформить заказ - заказ будет выведен в появившемся поле
* 5. для следующего заказа нажмите Новый заказ
* 6. для отмены текущего заказа нажмите Отмена
* 7. для выхода - Выйти
* Таблица лидеров продаж формируется по всем не отмененным заказам, очищается при выходе из программы.
* */
public class Menu extends Frame {

    public int[] leaders = new int[9]; // таблица лидеров: индекс - номер пиццы в списке
    public int[] saved_leaders = new int[9];// сохраненная с прошлого заказа таблица лидеров: индекс - номер пиццы в списке
    public int[] pizza_amounts = new int[9];// количество заказанных пицц: индекс - номер пиццы в списке
    public int[] pizza_prices = new int[9];// цены на пиццы: индекс - номер пиццы в списке
    public int selected_pizza_index;// индекс пиццы, выбранной из списка перед нажатием кнопки "Выбрать пиццу"
    public int selected_pizza_price; // цена пиццы, выбранной из списка перед нажатием кнопки "Выбрать пиццу"
    public int delivery_price; // цена доставки
    public int order_total;// сумма текущего заказа
    public int total;// сумма всех заказов
    public String order_text; // текст заказа

    /* графические компоненты: */
    public List pizza_list;
    private TextField district_field, address_field, time_field, delivery_price_field, price_field, total_price_field, phone_field;
    private TextArea total_sum_area, liders_area, amount_area, order_area;

    Menu(String s) {
        super(s);
        setLayout(null);
        setFont(new Font("Serif", Font.BOLD, 16));
        setBackground(new Color(153, 204, 255));
        Label district_label = new Label("Район доставки:");
        district_label.setBounds(20, 40, 140, 25);
        add(district_label);
        Label address_label = new Label("Адрес доставки:");
        address_label.setBounds(20, 75, 140, 25);
        add(address_label);
        Label time_label = new Label("Время доставки:");
        time_label.setBounds(20, 110, 140, 25);
        add(time_label);
        Label menu_label = new Label("Название блюд:");
        menu_label.setBounds(20, 145, 140, 25);
        add(menu_label);
        district_field = new TextField(30);
        district_field.setBounds(190, 40, 160, 25);
        add(district_field);
        address_field = new TextField(30);
        address_field.setBounds(190, 75, 160, 25);
        add(address_field);
        time_field = new TextField(30);
        time_field.setBounds(190, 110, 160, 25);
        add(time_field);
        pizza_list = new List(2, true);
        pizza_list.setMultipleMode(false);
        pizza_list.add("Калифорния");
        pizza_list.add("Аляска");
        pizza_list.add("Черри");
        pizza_list.add("Хиноки");
        pizza_list.add("Пицца 4 сыра");
        pizza_list.add("Пицца Класс");
        pizza_list.add("Пицца Гавайская");
        pizza_list.add("Пицца Мексиканская");
        pizza_list.add("Пицца Петровская");
        pizza_list.setBounds(20, 170, 200, 70);
        add(pizza_list);
        Label price_label = new Label("Цена выбранной пиццы:");
        price_label.setBounds(20, 280, 190, 30);
        add(price_label);
        delivery_price_field = new TextField(30);
        delivery_price_field.setEditable(true);
        delivery_price_field.setBounds(210, 245, 150, 25);
        add(delivery_price_field);
        Label delivery_price_label = new Label("Стоимость доставки:");
        delivery_price_label.setBounds(20, 245, 190, 30);
        add(delivery_price_label);
        price_field = new TextField(30);
        price_field.setEditable(true);
        price_field.setBounds(210, 280, 150, 25);
        add(price_field);
        Label total_price_label = new Label("Общая стоимость заказа:");
        total_price_label.setBounds(20, 315, 215, 30);
        add(total_price_label);
        total_price_field = new TextField(30);
        total_price_field.setEditable(true);
        total_price_field.setBounds(235, 315, 125, 25);
        add(total_price_field);
        Label phone_label = new Label("Контактный телефон:");
        phone_label.setBounds(20, 450, 185, 25);
        add(phone_label);
        phone_field = new TextField(30);
        phone_field.setEditable(true);
        phone_field.setBounds(215, 450, 150, 25);
        add(phone_field);
        Label l9 = new Label("рублей");
        l9.setBounds(370, 245, 80, 25);
        add(l9);
        Label l10 = new Label("рублей");
        l10.setBounds(370, 280, 80, 25);
        add(l10);
        Label l11 = new Label("рублей");
        l11.setBounds(370, 315, 80, 25);
        add(l11);

        total_sum_area = new TextArea("Сумма заказа с доставкой: 0 \n Общая сумма заказов: 0", 5, 50, TextArea.SCROLLBARS_NONE);
        total_sum_area.setEditable(false);
        total_sum_area.setBounds(600, 40, 250, 140);
        add(total_sum_area);
        liders_area = new TextArea("Лидеры продаж:", 5, 50, TextArea.SCROLLBARS_NONE);
        liders_area.setEditable(false);
        liders_area.setBounds(600, 190, 250, 220);
        add(liders_area);
        amount_area = new TextArea("Количество позиций:", 5, 50, TextArea.SCROLLBARS_NONE);
        amount_area.setEditable(false);
        amount_area.setBounds(900, 40, 250, 220);
        add(amount_area);
        order_area = new TextArea("", 5, 50, TextArea.SCROLLBARS_NONE);
        order_area.setEditable(false);
        order_area.setBounds(900, 280, 250, 350);
        order_area.setVisible(false);
        add(order_area);


        Button new_button = new Button("Новый заказ");
        new_button.setBounds(450, 270, 140, 30);
        add(new_button);
        Button apply_button = new Button("Выбрать пиццу");
        apply_button.setBounds(450, 310, 140, 30);
        add(apply_button);
        Button total_button = new Button("Оформить заказ");
        total_button.setBounds(450, 350, 140, 30);
        add(total_button);
        Button cancel_button = new Button("Отмена");
        cancel_button.setBounds(450, 430, 140, 30);
        add(cancel_button);
        Button exit_button = new Button("Выйти");
        exit_button.setBounds(450, 500, 140, 30);
        add(exit_button);

        /* инициализация переменных-накопителей */
        for (int i = 0; i < pizza_amounts.length; i++) pizza_amounts[i] = 0;
        for (int i = 0; i < leaders.length; i++) leaders[i] = 0;
        selected_pizza_index = 0;
        selected_pizza_price = 0;
        delivery_price = 0;
        order_total = 0;
        order_text = "Ваш заказ:\n";

        /* действия по нажатию кнопок и выбору в списке */
        new_button.addActionListener(new NewButtonPressing());
        apply_button.addActionListener(new ApplyButtonPressing());
        exit_button.addActionListener(new Close());
        total_button.addActionListener(new TotalButtonPressing());
        cancel_button.addActionListener(new CancelButtonPressing());
        pizza_list.addItemListener(new PizzaSelector());

        setSize(600, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        Frame f = new Menu(" Поля ввода");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
    }

    /* метод, определяющий стоимость доставки по введенному району.
    Если район не введен или введен неверно, стоимость равна нулю. */
    public void setDeliveryPrice() {
        String entered_district = (String) district_field.getText();
        switch (entered_district) {
            case "Калининский":
                delivery_price_field.setText("240");
                delivery_price = Integer.parseInt(delivery_price_field.getText());
                break;

            case "Выборгский":
                delivery_price_field.setText("210");
                delivery_price = Integer.parseInt(delivery_price_field.getText());
                break;

            case "Приморский":
                delivery_price_field.setText("270");
                delivery_price = Integer.parseInt(delivery_price_field.getText());
                break;

            case "Невский":
                delivery_price_field.setText("190");
                delivery_price = Integer.parseInt(delivery_price_field.getText());
                break;

            case "Красногвардейский":
                delivery_price_field.setText("340");
                delivery_price = Integer.parseInt(delivery_price_field.getText());
                break;

            case "Фрунзенский":
                delivery_price_field.setText("160");
                delivery_price = Integer.parseInt(delivery_price_field.getText());
                break;

            case "Петроградский":
                delivery_price_field.setText("300");
                delivery_price = Integer.parseInt(delivery_price_field.getText());
                break;

            case "Адмиралтейский":
                delivery_price_field.setText("120");
                delivery_price = Integer.parseInt(delivery_price_field.getText());
                break;
            default:
                delivery_price_field.setText("не выбран район");
                delivery_price = 0;
        }
    }

    /* метод, осуществляющий очистку полей и счетчиков для нового заказа; область отображения заказа прячется */
    public void clean_order() {
        district_field.setText(" ");
        address_field.setText(" ");
        time_field.setText(" ");
        delivery_price_field.setText(" ");
        price_field.setText(" ");
        total_price_field.setText(" ");
        total_sum_area.setText("Общая сумма заказов: " + total);
        phone_field.setText(" ");
        amount_area.setText("Количество позиций:");
        for (int i = 0; i < pizza_amounts.length; i++) pizza_amounts[i] = 0;
        selected_pizza_index = 0;
        selected_pizza_price = 0;
        delivery_price = 0;
        order_total = 0;
        order_text = "Ваш заказ:\n";
        order_area.setVisible(false);
    }

    /* класс следит за списком и запоминает индекс выбранной пиццы */
    class PizzaSelector implements ItemListener {
        public void itemStateChanged(ItemEvent ie) {
            String s1 = (String) pizza_list.getSelectedItem();
            switch (s1) {
                case "Калифорния":
                    selected_pizza_index = 0;
                    price_field.setText("225");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;

                case "Аляска":
                    selected_pizza_index = 1;
                    price_field.setText("149");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;

                case "Черри":
                    selected_pizza_index = 2;
                    price_field.setText("177");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;

                case "Хиноки":
                    selected_pizza_index = 3;
                    price_field.setText("149");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;

                case "Пицца 4 сыра":
                    selected_pizza_index = 4;
                    price_field.setText("390");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;

                case "Пицца Класс":
                    selected_pizza_index = 5;
                    price_field.setText("299");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;

                case "Пицца Гавайская":
                    selected_pizza_index = 6;
                    price_field.setText("420");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;

                case "Пицца Мексиканская":
                    selected_pizza_index = 7;
                    price_field.setText("430");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;

                case "Пицца Петровская":
                    selected_pizza_index = 8;
                    price_field.setText("399");
                    pizza_prices[selected_pizza_index] = Integer.parseInt(price_field.getText());
                    selected_pizza_price = pizza_prices[selected_pizza_index];
                    break;
            }

        }
    }

    /* класс обрабатывает нажатие кнопки "Выбрать пиццу":
    выводится стоимость доставки по выбранному району,
    стоимость пиццы, выбранной в списке, добавляется к стоимости текущего заказа,
    увеличивается счетчик пицц данного вида, выбранных в этом заказе,
    пицца добавляется в таблицу лидеров,
    измененные значения количества позиций и таблицы лидеров выводятся в окне */
    class ApplyButtonPressing implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            setDeliveryPrice();
            pizza_amounts[selected_pizza_index]++;
            leaders[selected_pizza_index]++;
            order_total = order_total + selected_pizza_price;
            amount_area.setText("Количество позиций: " + "\n"
                            + " Калифорния - " + pizza_amounts[0] + " (" + pizza_prices[0] * pizza_amounts[0] + " руб)\n"
                            + " Аляска - " + pizza_amounts[1] + " (" + pizza_prices[1] * pizza_amounts[1] + " руб)\n"
                            + " Черри - " + pizza_amounts[2] + " (" + pizza_prices[2] * pizza_amounts[2] + " руб)\n"
                            + " Хиноки - " + pizza_amounts[3] + " (" + pizza_prices[3] * pizza_amounts[3] + " руб)\n"
                            + " Пицца 4 сыра - " + pizza_amounts[4] + " (" + pizza_prices[4] * pizza_amounts[4] + " руб)\n"
                            + " Пицца Класс - " + pizza_amounts[5] + " (" + pizza_prices[5] * pizza_amounts[5] + " руб)\n"
                            + " Пицца Гавайская - " + pizza_amounts[6] + " (" + pizza_prices[6] * pizza_amounts[6] + " руб)\n"
                            + " Пицца Мексиканская - " + pizza_amounts[7] + " (" + pizza_prices[7] * pizza_amounts[7] + " руб)\n"
                            + " Пицца Петровская - " + pizza_amounts[8] + " (" + pizza_prices[8] * pizza_amounts[8] + " руб)\n"
            );
            liders_area.setText("Лидеры продаж: " + "\n"
                            + " Калифорния - " + leaders[0] + "\n"
                            + " Аляска - " + leaders[1] + "\n"
                            + " Черри - " + leaders[2] + "\n"
                            + " Хиноки - " + leaders[3] + "\n"
                            + " Пицца 4 сыра - " + leaders[4] + "\n"
                            + " Пицца Класс - " + leaders[5] + "\n"
                            + " Пицца Гавайская - " + leaders[6] + "\n"
                            + " Пицца Мексиканская - " + leaders[7] + "\n"
                            + " Пицца Петровская - " + leaders[8]
            );

        }
    }

    /* класс обрабатывает нажатие кнопки "Оформить заказ":
    определяется и выводится сумма заказа с учетом доставки и общая сумма заказов */
    class TotalButtonPressing implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            order_total = order_total + delivery_price;
            total_price_field.setText(Integer.toString(order_total));
            total = total + order_total;
            total_sum_area.setText("Сумма заказа с доставкой: " + order_total + " рублей \n" + "Общая сумма заказов: " + total);
            System.arraycopy(leaders, 0, saved_leaders, 0, leaders.length); // сохраняем таблицу лидеров
            order_text = order_text + "Адрес доставки:  " + address_field.getText() + "\n"
                    + "Телефон:" + phone_field.getText() + "\n"
                    + "Время доставки:" + time_field.getText() + "\n"
                    + "\n" + amount_area.getText() + "\n"
                    + "Сумма заказа: " + order_total + "\n";
            // Текстовая область становится видимой и отображает текст заказа
            order_area.setText(order_text);
            order_area.setVisible(true);
        }
    }

    /* класс обрабатывает нажатие кнопки "Новый заказ":
    все поля предыдущего заказа очищаются, остается только таблица лидеров и сумма предыдущих заказов */
    class NewButtonPressing implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            clean_order();
        }
    }

    /* класс обрабатывает нажатие кнопки "Отмена":
    все поля текущего заказа очищаются, таблица лидеров возвращается к резервной копии */
    class CancelButtonPressing implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            clean_order();
            System.arraycopy(saved_leaders, 0, leaders, 0, saved_leaders.length);
            liders_area.setText("Лидеры продаж: " + "\n"
                            + " Калифорния - " + leaders[0] + "\n"
                            + " Аляска - " + leaders[1] + "\n"
                            + " Черри - " + leaders[2] + "\n"
                            + " Хиноки - " + leaders[3] + "\n"
                            + " Пицца 4 сыра - " + leaders[4] + "\n"
                            + " Пицца Класс - " + leaders[5] + "\n"
                            + " Пицца Гавайская - " + leaders[6] + "\n"
                            + " Пицца Мексиканская - " + leaders[7] + "\n"
                            + " Пицца Петровская - " + leaders[8]
            );


        }
    }

    /* класс обрабатывает нажатие кнопки "Выйти" */
    class Close implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

}