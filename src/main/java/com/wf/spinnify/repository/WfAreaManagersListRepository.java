package com.wf.spinnify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wf.spinnify.entity.WfAreaManagersList;

public interface WfAreaManagersListRepository extends JpaRepository<WfAreaManagersList, Integer>{

	List<WfAreaManagersList> findAllByRmNameIgnoreCase(String name);

}
