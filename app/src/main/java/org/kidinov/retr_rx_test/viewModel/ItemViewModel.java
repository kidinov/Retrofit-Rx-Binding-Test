package org.kidinov.retr_rx_test.viewModel;

public class ItemViewModel {
    private String text;
    private String url;

    public ItemViewModel(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
