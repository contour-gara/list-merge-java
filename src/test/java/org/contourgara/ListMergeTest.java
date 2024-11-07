package org.contourgara;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.junit.jupiter.api.Test;

class ListMergeTest {
    @Test
    void addAllはインスタンスが変わらない() {
        // setup
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("a", new ArrayList<>(List.of(1, 2, 3)));
        map.put("b", new ArrayList<>(List.of(4, 5, 6)));

        Map<String, List<Integer>> updatedMap = new HashMap<>();
        updatedMap.put("a", new ArrayList<>(List.of(1, 2, 3)));
        updatedMap.put("b", new ArrayList<>(List.of(4, 5, 6)));

        List<Integer> list1 = updatedMap.get("a");
        List<Integer> list2 = List.of(4, 5, 6);

        // execute
        list1.addAll(list2);

        // assert
        assertThat(list1).isEqualTo(updatedMap.get("a"));
        assertThat(updatedMap).isNotEqualTo(map);
    }

    @Test
    void streamで連結すれば新規リストが作成される() {
        // setup
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("a", new ArrayList<>(List.of(1, 2, 3)));
        map.put("b", new ArrayList<>(List.of(4, 5, 6)));

        List<Integer> list1 = map.get("a");
        List<Integer> list2 = List.of(4, 5, 6);

        // execute
        List<Integer> list3 = Stream.concat(list1.stream(), list2.stream()).toList();

        // assert
        assertThat(list3).isNotEqualTo(map.get("a"));
        System.out.println(map);
    }

    @Test
    void streamで連結すればミュータブルな新規セットが作成される() {
        // setup
        Map<String, Set<Integer>> map = new HashMap<>();
        map.put("a", new HashSet<>(List.of(1, 2, 3)));
        map.put("b", new HashSet<>(List.of(4, 5, 6)));

        Set<Integer> set1 = map.get("a");
        Set<Integer> set2 = Set.of(4, 5, 6);

        // execute
        Set<Integer> set3 = Stream.concat(set1.stream(), set2.stream()).collect(Collectors.toSet());

        // assert
        assertThat(set3).isNotEqualTo(map.get("a"));
        System.out.println(map);
        set3.add(7);
        System.out.println(set3);
    }

    @Test
    void streamで連結すればイミュータブルな新規セットが作成される() {
        // setup
        Map<String, Set<Integer>> map = new HashMap<>();
        map.put("a", new HashSet<>(List.of(1, 2, 3)));
        map.put("b", new HashSet<>(List.of(4, 5, 6)));

        Set<Integer> set1 = map.get("a");
        Set<Integer> set2 = Set.of(4, 5, 6);

        // execute
        Set<Integer> set3 = Stream.concat(set1.stream(), set2.stream()).collect(Collectors.toUnmodifiableSet());

        // assert
        assertThat(set3).isNotEqualTo(map.get("a"));
        System.out.println(map);
        assertThatThrownBy(() -> set3.add(7))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void ListUtilsを使用すれば新規リストが作成される() {
        // setup
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("a", new ArrayList<>(List.of(1, 2, 3)));
        map.put("b", new ArrayList<>(List.of(4, 5, 6)));

        List<Integer> list1 = map.get("a");
        List<Integer> list2 = List.of(4, 5, 6);

        // execute
        List<Integer> list3 = ListUtils.union(list1, list2);

        // assert
        assertThat(list3).isNotEqualTo(map.get("a"));
        System.out.println(map);
    }

    @Test
    void SetUtilsを使用すれば新規セットが作成される() {
        // setup
        Map<String, Set<Integer>> map = new HashMap<>();
        map.put("a", new HashSet<>(List.of(1, 2, 3)));
        map.put("b", new HashSet<>(List.of(4, 5, 6)));

        Set<Integer> set1 = map.get("a");
        Set<Integer> set2 = Set.of(4, 5, 6);

        // execute
        Set<Integer> set3 = SetUtils.union(set1, set2);

        // assert
        assertThat(set3).isNotEqualTo(map.get("a"));
        System.out.println(map);
    }
}
