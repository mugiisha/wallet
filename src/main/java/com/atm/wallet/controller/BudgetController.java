package com.atm.wallet.controller;

import com.atm.wallet.dto.BudgetDto;
import com.atm.wallet.dto.UpdateBudgetDto;
import com.atm.wallet.model.Budget;
import com.atm.wallet.service.BudgetService;
import com.atm.wallet.util.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/budget")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public Response<Budget> createBudget(HttpServletRequest request,@RequestBody @Valid BudgetDto budgetDto){
        UUID userId =(UUID) request.getAttribute("userId");
        Budget budget = budgetService.createBudget(userId, budgetDto.getAmount());

        return new Response<>(true, "Budget created successfully", budget);
    }

    @PutMapping("/{budgetId}")
    public Response<Budget> updateBudget(@PathVariable UUID budgetId, @RequestBody @Valid UpdateBudgetDto budgetDto){
        Budget budget = budgetService.updateBudget(budgetId, budgetDto);

        return new Response<>(true, "Budget updated successfully", budget);
    }
}
