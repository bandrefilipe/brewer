/*
 * Copyright (c) 2020 André Barranco
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.bandrefilipe.brewer.web.controller;

import io.bandrefilipe.brewer.web.model.BeerRegistrationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * @author bandrefilipe
 * @since 1.0.0
 */
@Slf4j
@Controller
@RequestMapping(
        path = Paths.BEER_REGISTRATION,
        produces = MediaType.TEXT_HTML_VALUE)
class BeerRegistrationController implements IController {

    @GetMapping
    public ModelAndView getBeerRegistrationPage(final BeerRegistrationModel beerRegistrationModel) {
        log.debug("input: {}", beerRegistrationModel);
        final var modelAndView = new ModelAndView(ViewNames.BEER_REGISTRATION);
        modelAndView.addObject("origins", Arrays.asList("Domestic", "Imported"));
        return modelAndView;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postBeerRegistrationForm(@Valid final BeerRegistrationModel beerRegistrationModel,
                                                 final BindingResult bindingResult) {
        log.debug("input: {}", beerRegistrationModel);
        if (bindingResult.hasErrors()) {
            log.debug("errors: {}", bindingResult.getAllErrors());
            return getBeerRegistrationPage(beerRegistrationModel);
        }
        return new ModelAndView(redirect(Paths.BEER_REGISTRATION));
    }
}
