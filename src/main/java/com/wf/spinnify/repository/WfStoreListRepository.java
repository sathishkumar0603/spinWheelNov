package com.wf.spinnify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wf.spinnify.entity.WfStoreList;

public interface WfStoreListRepository extends JpaRepository<WfStoreList, Integer>{

	List<WfStoreList> findAllByRmNameIgnoreCase(String name);

}
