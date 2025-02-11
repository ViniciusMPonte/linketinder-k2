package utils

class GenericUtils {
    def static getDifferenceBetweenArrays(array1, array2) {
        def set1 = array1 as Set
        def set2 = array2 as Set
        def difference = set1 - set2
        return difference as List
    }
}
