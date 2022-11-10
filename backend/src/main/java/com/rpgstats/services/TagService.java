package com.rpgstats.services;

import com.rpgstats.entity.GameSystem;
import com.rpgstats.entity.SystemTag;
import com.rpgstats.messages.*;
import com.rpgstats.repositories.SystemRepository;
import com.rpgstats.repositories.SystemTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class TagService {
    SystemTagRepository tagRepository;
    SystemRepository systemRepository;
    ModelMapper mapper;

    public TagService(SystemTagRepository tagRepository, SystemRepository systemRepository, ModelMapper mapper) {
        this.tagRepository = tagRepository;
        this.systemRepository = systemRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<SystemTagDto> getTagsBySystem(Integer systemId) {
        return tagRepository.findByGameSystem_Id(systemId).stream().map(tag -> mapper.map(tag, SystemTagDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public SystemTagDto getTag(Integer systemId, Integer tagId) {
        return mapper.map(tagRepository.findByIdAndGameSystem_Id(tagId, systemId), SystemTagDto.class);
    }

    @Transactional
    public SystemTagDto createTag(Integer userId, Integer systemId, CreateTagPostRequest request){
        GameSystem system = systemRepository.findByIdAndOwner_Id(systemId, userId).orElseThrow();
        SystemTag tag = new SystemTag();
        tag.setName(request.getName());
        tag.setGameSystem(system);
        tagRepository.save(tag);
        return mapper.map(tag, SystemTagDto.class);
    }

    @Transactional
    public SystemTagDto changeTag(Integer userId, Integer tagId, Integer systemId, ChangeTagPutRequest request){
        GameSystem system = systemRepository.findByIdAndOwner_Id(systemId, userId).orElseThrow();
        SystemTag tag = tagRepository.findByIdAndGameSystem_IdAndGameSystem_Owner_Id(tagId, systemId, userId).orElseThrow();
        tag.setName(request.getName());
        tag.setGameSystem(system);
        tagRepository.save(tag);
        return mapper.map(tag, SystemTagDto.class);
    }

    @Transactional
    public SystemTagDto deleteTag(Integer userId, Integer tagId, Integer systemId){
        SystemTag tag = tagRepository.findByIdAndGameSystem_IdAndGameSystem_Owner_Id(tagId, systemId, userId).orElseThrow();
        tagRepository.delete(tag);
        return mapper.map(tag, SystemTagDto.class);
    }
}
