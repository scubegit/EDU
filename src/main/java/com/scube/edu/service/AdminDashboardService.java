package com.scube.edu.service;

import java.util.HashMap;
import java.util.List;


public interface AdminDashboardService {

	public HashMap<String,HashMap<String,Integer>> getRequestStatByStatus();
}
