package com.example.root.realheart.Model;

import java.util.List;

/**
 * Created by root on 14/4/18.
 */

public class MyResponse {
    public long multicast_id;
    public int success;
    public int failure;
    public int canonical_ids;
    public List<Result> results;
}
