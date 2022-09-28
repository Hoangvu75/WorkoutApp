package com.example.workoutapp

object ExerciseConstants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>
    {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(1, "1. Jumping Jacks:\nTargeted muscles - Calves,\nHips, Shoulders, Core", R.drawable.jumping_jacks, false, false)
        exerciseList.add(jumpingJacks)

        val wallSit = ExerciseModel(2, "2. Wall Sit:\nTargeted muscles - Thighs,\nHamstrings, Back, Core", R.drawable.wall_sit, false, false)
        exerciseList.add(wallSit)

        val pushUp = ExerciseModel(3, "3. Push Up:\nTargeted muscles - Chest,\nTriceps, Back, Shoulders, Core", R.drawable.push_up, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunches = ExerciseModel(4, "4. Abdominal Crunches:\nTargeted muscles - Abdominals", R.drawable.abdominal_crunches, false, false)
        exerciseList.add(abdominalCrunches)

        val stepUp = ExerciseModel(5, "5. Step Up:\nTargeted muscles - Thighs,\nGlutes, Hips, Core", R.drawable.step_up, false, false)
        exerciseList.add(stepUp)

        val airSquat = ExerciseModel(6, "6. Air Squat:\nTargeted muscles - Thighs,\nGlutes, Hips, Core", R.drawable.air_squat, false, false)
        exerciseList.add(airSquat)

        val chairDip = ExerciseModel(7, "7. Chair Dip:\nTargeted muscles - Triceps,\nShoulders, Chest", R.drawable.chair_dip, false, false)
        exerciseList.add(chairDip)

        val plank = ExerciseModel(8, "8. Plank:\nTargeted muscles - Abdominals,\nCore, Back, Glutes", R.drawable.plank, false, false)
        exerciseList.add(plank)

        val highKnees = ExerciseModel(9, "9. High Knees:\nTargeted muscles - Thighs,\nHips, Glutes", R.drawable.high_knees, false, false)
        exerciseList.add(highKnees)

        val lunges = ExerciseModel(10, "10. Lunges:\nTargeted Muscles - Thighs,\nCalves, Hips, Hamstrings, Core", R.drawable.lunges, false, false)
        exerciseList.add(lunges)

        val pushUpRotate = ExerciseModel(11, "11. Push Up With Rotation:\nTargeted Muscles - Chest,\nTriceps, Back, Shoulders, Core", R.drawable.pushup_rotate, false, false)
        exerciseList.add(pushUpRotate)

        val sidePlank = ExerciseModel(12, "12. Side Planks:\nTargeted Muscles - Core,\nAbdominals, Shoulders, Glutes", R.drawable.side_plank, false, false)
        exerciseList.add(sidePlank)

        return exerciseList
    }
}