package ccash.android.com.modle;

import java.util.List;

public class BulletinListResponse extends BaseBean {

    private List<BulletinListItem> list;

    public List<BulletinListItem> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "BulletinListResponse{" +
                "list=" + list +
                '}';
    }
}
