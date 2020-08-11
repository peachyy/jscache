package com.peachyy.xcache.service.impl;

import com.peachyy.xcache.service.OrderService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Xs.Tao
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    @Cacheable(cacheNames = "xxx",key = "'1'")

    public String say() {
        return null;
    }

//    public static void main(String[] args) {
//        UserServiceImpl userService       =new UserServiceImpl();
//        Method          method            =ReflectionUtils.findMethod(userService.getClass(),"getUserById",Integer.class);
//        Set<Class<? extends Annotation>> set =new HashSet<>();
//        set.add(Cacheable.class);
//        set.add(CacheEvict.class);
//        java.util.Set<Annotation> aa=
//        AnnotatedElementUtils.getAllMergedAnnotations(method, set);
//        System.out.println(aa);
//
//        java.util.Set<Annotation> aa2=
//                AnnotatedElementUtils.findAllMergedAnnotations(method, set);
//        System.out.println(aa2);
//    }
}
