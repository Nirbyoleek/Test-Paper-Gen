package lj.srm.qgen.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import lj.srm.qgen.api.FakeQuestionsRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest{



    private lateinit var viewModel: MainViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        viewModel = MainViewModel(FakeQuestionsRepository())
    }

    @Test
    fun `unit test setup test, returns true`(){

        assertThat("@lowjunkie").isEqualTo("@lowjunkie")

    }

    @Test
    fun `unit test setup test, returns false`(){

        assertThat("@lowjunkie").isNotEqualTo("!@lowjunkie")

    }

}