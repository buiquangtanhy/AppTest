package com.example.appTest.ui



class WorkoutInstance {

    private var page: Int = 1

    companion object {
        private var INSTANCE: WorkoutInstance? = null

        fun getInstance(): WorkoutInstance {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = WorkoutInstance()
                INSTANCE = instance
                instance
            }
        }
    }

    fun getPage(): Int {
        return page
    }

    fun setPage(p: Int) {
        page = p
    }

}